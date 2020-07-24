package com.konovalov.converter.controller;

import com.konovalov.converter.entity.Currency;
import com.konovalov.converter.model.ConverterModel;
import com.konovalov.converter.service.ConverterService;
import com.konovalov.converter.service.CurrenciesManager;
import com.konovalov.converter.service.RatesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ConverterController {

    @Autowired
    private CurrenciesManager currenciesManager;

    @Autowired
    private RatesManager ratesManager;

    @Autowired
    private ConverterService converterService;

    @GetMapping("/converter")
    public String showConverter(@ModelAttribute ConverterModel converterModel) {
        if (ratesManager.areRatesOutdated()) {
            currenciesManager.updateCurrencies();
            ratesManager.updateRates();
        }
        List<Currency> currencies = currenciesManager.findCurrenciesWithRelevantRate();
        converterModel.setCurrenciesFrom(currencies);
        converterModel.setCurrenciesTo(currencies);
        return "converter";
    }

    @PostMapping("/converter")
    public String doConversion(
            @ModelAttribute @Valid ConverterModel converterModel,
            BindingResult bindingResult) {
        List<Currency> currencies = currenciesManager.findCurrenciesWithRelevantRate();
        converterModel.setCurrenciesFrom(currencies);
        converterModel.setCurrenciesTo(currencies);
        if (bindingResult.hasErrors()) return "converter";
        BigDecimal outputValue = converterService.convert(
                converterModel.getInputValue(),
                converterModel.getCurrencyFromId(),
                converterModel.getCurrencyToId()
        );
        converterModel.setOutputValue(outputValue);
        return "converter";
    }

}
