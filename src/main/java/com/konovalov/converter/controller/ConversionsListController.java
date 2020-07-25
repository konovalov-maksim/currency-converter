package com.konovalov.converter.controller;


import com.konovalov.converter.service.ConversionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConversionsListController {

    //TODO перенести в конструктор
    @Autowired
    private ConversionsService conversionsService;

    @GetMapping("conversions")
    public String showConversionsList(Model model) {
        //TODO определять id пользователя автоматом
        model.addAttribute("conversionsList", conversionsService.findUserConversions(1L));


        return "conversions";
    }

}
