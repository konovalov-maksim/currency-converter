package com.konovalov.converter.controller;


import com.konovalov.converter.service.ConversionsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConversionsListController {

    private final ConversionsService conversionsService;

    public ConversionsListController(ConversionsService conversionsService) {
        this.conversionsService = conversionsService;
    }

    //TODO реализовать фильтры и пагинацию
    @GetMapping("conversions")
    public String showConversionsList(Model model) {
        model.addAttribute("conversionsList", conversionsService.findUserConversions());
        return "conversions";
    }

}
