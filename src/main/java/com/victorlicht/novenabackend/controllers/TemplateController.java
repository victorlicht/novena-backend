package com.victorlicht.novenabackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {

    @GetMapping("/home") // Define your endpoint path
    public String yourPage() {
        // Add any necessary model attributes if needed
        return "home";
    }
    @GetMapping("/admin") // Define your endpoint path
    public String admin() {
        // Add any necessary model attributes if needed
        return "admin";
    }
}
