package com.konovalov.converter.controller;

import com.konovalov.converter.model.entity.Currency;
import com.konovalov.converter.model.entity.User;
import com.konovalov.converter.model.dto.ConverterModel;
import com.konovalov.converter.service.ConversionsService;
import com.konovalov.converter.service.CurrenciesService;
import com.konovalov.converter.service.RatesService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ConverterController {

    @Value("${view.defaultCurrencyFromId}")
    private String defaultCurrencyFromId;
    @Value("${view.defaultCurrencyToId}")
    private String defaultCurrencyToId;
    @Value("${view.defaultInputValue}")
    private int defaultInputValue;

    private final CurrenciesService currenciesService;
    private final RatesService ratesService;
    private final ConversionsService conversionsService;

    @GetMapping("/converter")
    public String showConverter(@ModelAttribute ConverterModel converterModel) {
        if (ratesService.areRatesOutdated()) {
            currenciesService.updateCurrencies();
            ratesService.updateRates();
        }
        List<Currency> currencies = currenciesService.findCurrenciesWithRelevantRate();
        converterModel.setCurrenciesFrom(currencies);
        converterModel.setCurrenciesTo(currencies);
        if (converterModel.getCurrencyFromId() == null)
            converterModel.setCurrencyFromId(defaultCurrencyFromId);
        if (converterModel.getCurrencyToId() == null)
            converterModel.setCurrencyToId(defaultCurrencyToId);
        if (converterModel.getInputValue() == null)
            converterModel.setInputValue(new BigDecimal(defaultInputValue));
        return "converter";
    }

    @PostMapping("/converter")
    public String doConversion(
            @ModelAttribute @Valid ConverterModel converterModel,
            BindingResult bindingResult,
            Authentication auth) {
        List<Currency> currencies = currenciesService.findCurrenciesWithRelevantRate();
        converterModel.setCurrenciesFrom(currencies);
        converterModel.setCurrenciesTo(currencies);
        if (bindingResult.hasErrors()) return "converter";
        try {
            BigDecimal outputValue = conversionsService.convert(
                    converterModel.getInputValue(),
                    converterModel.getCurrencyFromId(),
                    converterModel.getCurrencyToId(),
                    (User) auth.getPrincipal()
            );
            converterModel.setOutputValue(outputValue);
        } catch (Exception e) {
            converterModel.setHasErrors(true);
        }
        return "converter";
    }

}
