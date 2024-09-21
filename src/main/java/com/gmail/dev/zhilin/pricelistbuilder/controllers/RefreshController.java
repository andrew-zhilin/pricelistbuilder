package com.gmail.dev.zhilin.pricelistbuilder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.gmail.dev.zhilin.pricelistbuilder.services.UpdateService;
import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/refresh")
public class RefreshController {

    @Autowired
    private UpdateService updateService;
    
    @GetMapping
    public String getPage(Model model) {
        model.addAttribute("active", updateService.isActive());
        
        return "refresh";
    }

    @PostMapping
    public String getPricelist(HttpServletResponse response) {
        updateService.update();
        
        return "/refresh";
    }

}
