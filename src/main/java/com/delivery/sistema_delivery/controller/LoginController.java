package com.delivery.sistema_delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    //método para exibir a página de login
    @GetMapping("/login")
    public String showLoginForm() {
        return "login"; //retorna o nome do seu arquivo HTML
    }

    //método para processar o formulário de login
    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String senha, RedirectAttributes redirectAttributes) {
        if ("dyel12@gmail.com".equals(email) && "12345".equals(senha)) {
            return "redirect:/restaurantes";
        } else {
            redirectAttributes.addAttribute("error", true);
            return "redirect:/login";
        }
    }

    @GetMapping("/registrar")
    public String showRegisterForm() {
        return "registro";
    }

    @GetMapping("/")
    public String redirectToLogin() {
        // apenas redirecione diretamente.
        return "redirect:/login";
    }
}