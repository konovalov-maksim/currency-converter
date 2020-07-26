package com.konovalov.converter.controller;


import com.konovalov.converter.entity.Conversion;
import com.konovalov.converter.entity.Currency;
import com.konovalov.converter.entity.User;
import com.konovalov.converter.model.ConversionsFilterModel;
import com.konovalov.converter.service.ConversionsService;
import com.konovalov.converter.service.CurrenciesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class ConversionsListController {

    private final ConversionsService conversionsService;
    private final CurrenciesService currenciesService;

    @Autowired
    public ConversionsListController(ConversionsService conversionsService, CurrenciesService currenciesService) {
        this.conversionsService = conversionsService;
        this.currenciesService = currenciesService;
    }

    @GetMapping("conversions")
    public String showConversionsList(
            Model model,
            @ModelAttribute ConversionsFilterModel filter,
            Authentication auth) {
        //Получение данных для фильтров
        List<Currency> currencies = currenciesService.findAllCurrencies();
        model.addAttribute("currencies", currencies);
        model.addAttribute("filter", filter);

        //Получение данных пагинации
        //TODO довести до ума
        Pageable pageParams = PageRequest.of(0, 20, Sort.Direction.DESC, "date");

        //Получение списка конвертаций
        Page<Conversion> conversionsPage = conversionsService.findUserConversions(((User) auth.getPrincipal()), filter, pageParams);
        model.addAttribute("conversionsList", conversionsPage.getContent());
        return "conversions";
    }

}
