package com.mysite.sbb.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String adminIndex() {
        return "/admin/index";
    }

    @GetMapping("/error")
    public String adminError(){
        return "/admin/templates/pages/error";
    }

}
