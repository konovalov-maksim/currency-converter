package com.konovalov.converter.controller;

import com.konovalov.converter.entity.Currency;
import com.konovalov.converter.entity.User;
import com.konovalov.converter.model.ConverterModel;
import com.konovalov.converter.service.ConversionsService;
import com.konovalov.converter.service.CurrenciesManager;
import com.konovalov.converter.service.RatesManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
public class ConverterController {

    @Value("${default.currencyFromId}")
    private String defaultCurrencyFromId;

    @Value("${default.currencyToId}")
    private String defaultCurrencyToId;

    private final CurrenciesManager currenciesManager;
    private final RatesManager ratesManager;
    private final ConversionsService conversionsService;

    @Autowired
    public ConverterController(CurrenciesManager currenciesManager, RatesManager ratesManager, ConversionsService conversionsService) {
        this.currenciesManager = currenciesManager;
        this.ratesManager = ratesManager;
        this.conversionsService = conversionsService;
    }

    @GetMapping("/converter")
    public String showConverter(@ModelAttribute ConverterModel converterModel) {
        if (ratesManager.areRatesOutdated()) {
            currenciesManager.updateCurrencies();
            ratesManager.updateRates();
        }
        List<Currency> currencies = currenciesManager.findCurrenciesWithRelevantRate();
        converterModel.setCurrenciesFrom(currencies);
        converterModel.setCurrenciesTo(currencies);
        if (converterModel.getCurrencyFromId() == null)
            converterModel.setCurrencyFromId(defaultCurrencyFromId);
        if (converterModel.getCurrencyToId() == null)
            converterModel.setCurrencyToId(defaultCurrencyToId);
        return "converter";
    }

    @PostMapping("/converter")
    public String doConversion(
            @ModelAttribute @Valid ConverterModel converterModel,
            BindingResult bindingResult,
            Authentication auth) {
        List<Currency> currencies = currenciesManager.findCurrenciesWithRelevantRate();
        converterModel.setCurrenciesFrom(currencies);
        converterModel.setCurrenciesTo(currencies);
        if (bindingResult.hasErrors()) return "converter";
        BigDecimal outputValue = conversionsService.convert(
                converterModel.getInputValue(),
                converterModel.getCurrencyFromId(),
                converterModel.getCurrencyToId(),
                (User) auth.getPrincipal()
        );
        converterModel.setOutputValue(outputValue);
        return "converter";
    }

}
