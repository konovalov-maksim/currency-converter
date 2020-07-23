package com.konovalov.converter.controller;

import com.konovalov.converter.service.CurrenciesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConverterController {

    @Autowired
    private CurrenciesManager currenciesManager;

    @GetMapping("/converter")
    public String showConverter() {
        currenciesManager.updateCurrencies();
        return "converter";
    }
}
