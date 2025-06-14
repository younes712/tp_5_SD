package com.younes.gestionproduct.web;


import com.younes.gestionproduct.Repository.ProductRepository;
import com.younes.gestionproduct.entities.Product;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController  {
    @Autowired
    private ProductRepository productRepository;

    @GetMapping({"/user","/"})
    public String userHome(){
        return "redirect:/user/index";
    }

    @GetMapping("/admin")
    public String adminHome(){
        return "redirect:/admin/index";
    }

    @GetMapping({"/user/index","/admin/index"})
    public String index(Model model, @RequestParam(name ="page",defaultValue = "0") int page,
                                     @RequestParam(name ="size",defaultValue = "5") int size){
        //List<Product> products = productRepository.findAll();
        Page<Product>   productsPage = productRepository.findAll(PageRequest.of(page, size));
        model.addAttribute("products", productsPage.getContent());
        model.addAttribute("pages", new int [productsPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        return "products";
    }

    @PostMapping("/admin/delete")
    public String delete(@RequestParam("id") Long id){
        productRepository.deleteById(id);
        return "redirect:/admin/index";
    }

    @GetMapping("/admin/newProduct")
    public String newProduct(Model model){
        model.addAttribute("product", new Product());
        return "formProduct";
    }

    @PostMapping("/admin/save")
    public String save(@Valid  Product product, BindingResult result,Model model){
        if (result.hasErrors()){
            return "formProduct";
        }
        productRepository.save(product);
        return "redirect:/admin/newProduct";
    }

    @PostMapping("/admin/editProduct")
    public String editProduct(@RequestParam("id") Long id, Model model){
        Product product = productRepository.findById(id).get();
        model.addAttribute("product", product);
        return "editProduct";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(Model model){
        return "notAuthorized";
    }

    @GetMapping({"/user/search","/admin/search"})
    public String search(Model model, @RequestParam("key") String key,
                                      @RequestParam(name ="page",defaultValue = "0") int page,
                                      @RequestParam(name ="size",defaultValue = "5") int size){
        Page<Product> productPage;
        Pageable pageable = PageRequest.of(page, size);
        try {
            Long id = Long.parseLong(key);
            productPage = productRepository.findByIdOrNameContainingIgnoreCase(id, key, pageable);
        } catch (NumberFormatException e) {
            productPage = productRepository.findByNameContainingIgnoreCase(key, pageable);
        }

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("pages", new int[productPage.getTotalPages()]);
        model.addAttribute("key", key);
        model.addAttribute("currentPage", page);
        return "products";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}
