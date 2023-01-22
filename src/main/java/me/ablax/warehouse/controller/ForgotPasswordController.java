package me.ablax.warehouse.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import me.ablax.warehouse.entities.UserEntity;
import me.ablax.warehouse.models.UserDto;
import me.ablax.warehouse.models.req.ForgotReq;
import me.ablax.warehouse.models.req.RegisterReq;
import me.ablax.warehouse.models.req.ResetReq;
import me.ablax.warehouse.services.UserService;
import me.ablax.warehouse.utils.AuthenticationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class ForgotPasswordController {
    public static final String REQ_OBJ = "reqObj";
    public static final String EXCEPTION = "exception";
    private static final String HOME_PAGE = "redirect:/";

    private final UserService userService;

    public ForgotPasswordController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/forgot")
    public String forgot(final HttpSession httpSession, final Model model) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        return forgotPage(model);
    }

    @PostMapping(value = "/forgot")
    public String reset(final HttpSession httpSession, final Model model, @ModelAttribute(REQ_OBJ) @Valid final ForgotReq forgotReq, BindingResult bindingResult) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        final String bindingError;
        if ((bindingError = parseError(bindingResult)) != null) {
            return forgotPage(model, bindingError);
        }

        try{
            userService.resetPassword(forgotReq.getEmail());
        }catch (RuntimeException ex){
            return forgotPage(model, ex.getMessage());
        }

        return forgotPage(model, "Password reset email sent successfully!");
    }

    @GetMapping(value = "/reset/{token}")
    public String resetPasswordView(final Model model, final HttpSession session, @PathVariable final String token) {
        if (AuthenticationUtils.isLoggedIn(session)) {
            return HOME_PAGE;
        }

        return resetPage(model, token);
    }

    @PostMapping(value = "/reset/{token}")
    public String resetPasswordAction (final HttpSession httpSession, final Model model, @PathVariable final String token, @ModelAttribute(REQ_OBJ) @Valid final ResetReq resetReq, BindingResult bindingResult) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        model.addAttribute("token", token);
        final String bindingError;
        if ((bindingError = parseError(bindingResult)) != null) {
            return resetPage(model, bindingError, token);
        }

        final UserDto userDto;
        try {
            userDto = userService.changeUserPass(resetReq);
        } catch (RuntimeException ex) {
            return resetPage(model, ex.getMessage(), token);
        }
        AuthenticationUtils.login(httpSession, userDto);
        return HOME_PAGE;
    }

    private String parseError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            final StringBuilder stringBuilder = new StringBuilder();
            for (final ObjectError error : bindingResult.getAllErrors()) {
                if (error instanceof final FieldError fieldError) {
                    final String defaultMessage = error.getDefaultMessage();
                    stringBuilder.append("The field ").append(fieldError.getField()).append(" ").append(defaultMessage).append('!');
                }
            }
            return stringBuilder.isEmpty() ? "Unknown error" : stringBuilder.toString();
        }
        return null;
    }

    private String forgotPage(final Model model, final String ex) {
        model.addAttribute(EXCEPTION, ex);
        return forgotPage(model);
    }

    private String forgotPage(final Model model) {
        model.addAttribute(REQ_OBJ, new ForgotReq());
        return "forgot";
    }

    private String resetPage(final Model model, final String ex, final String token) {
        model.addAttribute(EXCEPTION, ex);
        return resetPage(model, token);
    }

    private String resetPage(final Model model, final String token) {
        final Optional<UserEntity> userByToken = userService.getUserByToken(token);
        if(userByToken.isEmpty()){
            return HOME_PAGE;
        }
        final UserEntity userEntity = userByToken.get();
        final ResetReq attributeValue = new ResetReq();
        attributeValue.setId(userEntity.getId());
        model.addAttribute("token", token);
        model.addAttribute(REQ_OBJ, attributeValue);
        return "reset";
    }
}
