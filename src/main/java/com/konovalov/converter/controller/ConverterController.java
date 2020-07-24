package com.konovalov.converter.controller;

import com.konovalov.converter.service.CurrenciesManager;
import com.konovalov.converter.service.RatesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConverterController {

    @Autowired
    private CurrenciesManager currenciesManager;

    @Autowired
    private RatesManager ratesManager;

    @GetMapping("/converter")
    public String showConverter() {
        if (ratesManager.areRatesOutdated()) {
            currenciesManager.updateCurrencies();
            ratesManager.updateRates();
        }
        return "converter";
    }
}
