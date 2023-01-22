package me.ablax.warehouse.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import me.ablax.warehouse.entities.ProductEntity;
import me.ablax.warehouse.models.req.ProductReq;
import me.ablax.warehouse.models.req.SearchReq;
import me.ablax.warehouse.services.ProductService;
import me.ablax.warehouse.utils.AuthenticationUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;


@Controller
@RequestMapping("/")
public class MainController {

    public static final String REQ_OBJ = "reqObj";
    private static final String HOME_PAGE = "redirect:/";
    private final ProductService productService;
    private final File imagesDir;

    public MainController(final ProductService productService) {
        this.productService = productService;
        imagesDir = new File("images");
        if (!imagesDir.exists()) {
            imagesDir.mkdir();
        }
    }

    @GetMapping(value = "/")
    public String homePage(final Model model, final SearchReq searchReq, final HttpSession session) {
        if (searchReq.getPage() == 0) {
            searchReq.setPage(1);
        }
        if (searchReq.getSize() == 0) {
            searchReq.setSize(2);
        }
        if (AuthenticationUtils.isLoggedIn(session)) {
            if (searchReq.getSearchCategory() != null) {
                session.setAttribute("lastSearch", searchReq.toQuery());
            }
            final Page<ProductEntity> products = productService.getAllProducts(searchReq, searchReq.getPage() - 1, searchReq.getSize());
            model.addAttribute("products", products);
            model.addAttribute("query", searchReq);
            int totalPages = products.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed().toList();
                model.addAttribute("pageNumbers", pageNumbers);
            }
            return "home";
        } else {
            return "redirect:/login";
        }
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
            final File imageToWrite = new File(imagesDir, UUID.randomUUID() + "" + productReq.getFile().getOriginalFilename());
            try (final FileOutputStream fileOutputStream = new FileOutputStream(imageToWrite)) {
                fileOutputStream.write(productReq.getFile().getBytes());
                fileOutputStream.flush();
            }
            productReq.setPicture(imageToWrite.getName());
        }

        productService.createOrUpdateProduct(productReq);

        if (productReq.getId() != null) {
            final Object lastSearch = session.getAttribute("lastSearch");
            if (lastSearch != null) {
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

}
