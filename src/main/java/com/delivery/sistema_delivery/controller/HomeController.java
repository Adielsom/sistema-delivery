package com.delivery.sistema_delivery.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // @GetMapping("/")
    // public String home() {
    //     return "redirect:/restaurantes";
    // }

    @GetMapping("/home-antiga")
    public String homeAntiga() {
        return "redirect:/restaurantes";
    }
}