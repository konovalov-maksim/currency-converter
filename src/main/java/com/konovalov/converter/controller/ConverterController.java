package com.konovalov.converter.controller;

import com.konovalov.converter.service.CurrencyManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConverterController {

    @Autowired
    private CurrencyManager currencyManager;

    @GetMapping("/converter")
    public String showConverter() {
        currencyManager.updateCurrencies();
        return "converter";
    }
}
