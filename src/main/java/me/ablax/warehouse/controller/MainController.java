package me.ablax.warehouse.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import me.ablax.warehouse.models.UserDto;
import me.ablax.warehouse.models.req.LoginReq;
import me.ablax.warehouse.models.req.ProductReq;
import me.ablax.warehouse.models.req.RegisterReq;
import me.ablax.warehouse.models.req.SearchReq;
import me.ablax.warehouse.services.ProductService;
import me.ablax.warehouse.services.UserService;
import me.ablax.warehouse.utils.AuthenticationUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


@Controller
@RequestMapping("/")
public class MainController {

    public static final String REQ_OBJ = "reqObj";
    public static final String EXCEPTION = "exception";
    private static final String HOME_PAGE = "redirect:/";
    private final UserService userService;
    private final ProductService productService;
    private final File imagesDir;

    public MainController(final UserService userService, final ProductService productService) {
        this.userService = userService;
        this.productService = productService;
        imagesDir = new File("images");
        if (!imagesDir.exists()) {
            imagesDir.mkdir();
        }
    }

    @GetMapping(value = "/")
    public String homePage(final Model model, final SearchReq searchReq, final HttpSession session) {
        if (AuthenticationUtils.isLoggedIn(session)) {
            if(searchReq.getSearchCategory() != null){
                session.setAttribute("lastSearch", searchReq.toQuery());
            }
            model.addAttribute("products", productService.getAllProducts(searchReq));
            model.addAttribute("query", searchReq);
            return "home";
        } else {
            return "redirect:/login";
        }
    }

    @GetMapping("/login")
    public String loginView(final Model model, final HttpSession httpSession) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        return loginPage(model);
    }

    @PostMapping("/login")
    public String loginAttempt(final Model model, final HttpSession httpSession, @ModelAttribute(REQ_OBJ) @Valid final LoginReq loginReq, BindingResult bindingResult) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        final String bindingError;
        if ((bindingError = parseError(bindingResult)) != null) {
            return loginPage(model, bindingError);
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

    @GetMapping(value = "/product")
    public String createProductView(final Model model, final HttpSession session) {
        if (AuthenticationUtils.isLoggedIn(session)) {
            model.addAttribute("title", "Create product");
            model.addAttribute(REQ_OBJ, new ProductReq());
            return "product";
        } else {
            return HOME_PAGE;
        }

    }

    @GetMapping(value = "/product/{id}")
    public String editProductView(final Model model, final HttpSession session, @PathVariable final String id) {
        if (AuthenticationUtils.isLoggedIn(session)) {
            model.addAttribute("title", "Edit product");
            model.addAttribute(REQ_OBJ, productService.getProduct(id));
            return "product";
        } else {
            return HOME_PAGE;
        }
    }

    @DeleteMapping(value = "/product/{id}")
    public ResponseEntity<String> deleteProductAction(final HttpSession session, @PathVariable final String id) {
        if (AuthenticationUtils.isLoggedIn(session)) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/product")
    public String createProductAction(final HttpSession session, @ModelAttribute(REQ_OBJ) @Valid final ProductReq productReq) throws IOException {
        if (!AuthenticationUtils.isLoggedIn(session)) {
            return HOME_PAGE;
        }
        if (productReq.getFile() != null && !productReq.getFile().isEmpty()) {
            final File imageToWrite = new File(imagesDir, AuthenticationUtils.getUser(session).username() + "" + productReq.getFile().getOriginalFilename());
            try (final FileOutputStream fileOutputStream = new FileOutputStream(imageToWrite)) {
                fileOutputStream.write(productReq.getFile().getBytes());
                fileOutputStream.flush();
            }
            productReq.setPicture(imageToWrite.getName());
        }

        productService.createOrUpdateProduct(productReq);

        if(productReq.getId() != null){
            final Object lastSearch = session.getAttribute("lastSearch");
            if(lastSearch!=null){
                return HOME_PAGE + lastSearch;
            }
        }
        return HOME_PAGE;
    }

    @GetMapping("image/{imageFile}")
    public void renderImageFromDb(@PathVariable final String imageFile, HttpServletResponse response) throws IOException {
        final File image = new File(imagesDir, imageFile);
        try (final FileInputStream fileReader = new FileInputStream(image);
             final BufferedInputStream bufferedReader = new BufferedInputStream(fileReader);) {

            IOUtils.copy(bufferedReader, response.getOutputStream());
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
    public String register(final HttpSession httpSession, final Model model, @ModelAttribute(REQ_OBJ) @Valid final RegisterReq registerReq, BindingResult bindingResult) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        final String bindingError;
        if ((bindingError = parseError(bindingResult)) != null) {
            return registerPage(model, bindingError);
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

  /*  @GetMapping(value = "/forgot")
    public String forgot(final HttpSession httpSession, final Model model) {
        if (AuthenticationUtils.isLoggedIn(httpSession)) {
            return HOME_PAGE;
        }
        model.addAttribute(REQ_OBJ, new ForgotReq());
        return "forgot";
    }

    @PostMapping(value = "/reset")
    public String reset(final HttpSession httpSession, final Model model, @ModelAttribute(REQ_OBJ) @Valid final ForgotReq forgotReq, BindingResult bindingResult) {
        *//*Handle forgot password login*//*
        final String bindingError;
        if ((bindingError = parseError(bindingResult)) != null) {
            return registerPage(model, bindingError);
        }
        return HOME_PAGE;
    }*/


    @RequestMapping(value = "/logout")
    public String logout(final HttpSession session) {
        session.invalidate();
        return HOME_PAGE;
    }


    /*Internal Stuff*/

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

    private String registerPage(final Model model, final String ex) {
        model.addAttribute(EXCEPTION, ex);
        return registerPage(model);
    }

    private String registerPage(final Model model) {
        model.addAttribute(REQ_OBJ, new RegisterReq());
        return "register";
    }

    private String loginPage(final Model model, final String ex) {
        model.addAttribute(EXCEPTION, ex);
        return loginPage(model);
    }

    private String loginPage(final Model model) {
        model.addAttribute(REQ_OBJ, new LoginReq());
        return "login";
    }
}
