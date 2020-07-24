package com.konovalov.converter.controller;

import com.konovalov.converter.service.ConversionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class ConversionsListController {

    //TODO перенести в конструктор
    @Autowired
    private ConversionService conversionService;

    @GetMapping("conversions")
    public String showConversionsList() {


        return "conversions";
    }

}
