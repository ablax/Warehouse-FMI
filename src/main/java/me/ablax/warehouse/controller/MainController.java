package me.ablax.warehouse.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import me.ablax.warehouse.models.req.ForgotReq;
import me.ablax.warehouse.models.req.LoginReq;
import me.ablax.warehouse.models.req.RegisterReq;
import me.ablax.warehouse.models.UserDto;
import me.ablax.warehouse.services.UserService;
import me.ablax.warehouse.utils.AuthenticationUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    private static final String HOME_PAGE = "redirect:/";
    private final UserService userService;

    public MainController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/")
    public String homePage(final Model model, final HttpSession session) {
        if (AuthenticationUtils.isLoggedIn(session)) {
//            model.addAttribute("photos", photoService.getAllPhotos(LoginUtils.getUser(session)));
            model.addAttribute("title", "Latest posts");
            model.addAttribute("redirect", "/my");
            return "home";
        } else {
            return loginPage(model);
        }
    }

    @GetMapping(value = "/register")
    public String register(final HttpSession httpSession, final Model model) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        return registerPage(model);
    }

    @PostMapping(value = "/register")
    public String register(final HttpSession httpSession, final Model model, @ModelAttribute("reqObj") @Valid final RegisterReq registerReq) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        final UserDto userDto;
        try {
            userDto = userService.registerUser(registerReq);
        } catch (RuntimeException ex) {
            return registerPage(model, ex.getMessage());
        }
        AuthenticationUtils.login(httpSession, userDto);
        return HOME_PAGE;
    }

    @GetMapping(value = "/forgot")
    public String forgot(final HttpSession httpSession, final Model model) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        model.addAttribute("reqObj", new ForgotReq());
        return "forgot";
    }

    @PostMapping(value = "/reset")
    public String reset(final HttpSession httpSession, final Model model, @ModelAttribute("reqObj") @Valid final ForgotReq forgotReq) {
        /*Handle forgot password login*/
        return HOME_PAGE;
    }

    @PostMapping("/login")
    public String loginAttempt(final Model model, final HttpSession httpSession, @ModelAttribute("reqObj") @Valid final LoginReq loginReq) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        final UserDto userDto;
        try {
            userDto = userService.loginUser(loginReq);
        } catch (RuntimeException ex) {
            return loginPage(model, ex.getMessage());
        }
        AuthenticationUtils.login(httpSession, userDto);
        return HOME_PAGE;
    }


    @RequestMapping(value = "/logout")
    public String logout(final HttpSession session) {
        session.invalidate();
        return HOME_PAGE;
    }


    /*Internal Stuff*/

    private String registerPage(final Model model, final String ex) {
        model.addAttribute("exception", ex);
        return registerPage(model);
    }

    private String registerPage(final Model model) {
        model.addAttribute("reqObj", new RegisterReq());
        return "register";
    }

    private String loginPage(final Model model, final String ex) {
        model.addAttribute("exception", ex);
        return loginPage(model);
    }

    private String loginPage(final Model model) {
        model.addAttribute("reqObj", new LoginReq());
        return "login";
    }
}
