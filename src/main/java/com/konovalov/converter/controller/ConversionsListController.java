package com.konovalov.converter.controller;


import com.konovalov.converter.model.entity.Conversion;
import com.konovalov.converter.model.entity.Currency;
import com.konovalov.converter.model.entity.User;
import com.konovalov.converter.model.dto.ConversionsFilterModel;
import com.konovalov.converter.model.dto.PaginationModel;
import com.konovalov.converter.service.ConversionsService;
import com.konovalov.converter.service.CurrenciesService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ConversionsListController {

    @Value("${view.itemsPerPage}")
    private int itemsPerPage;

    private final ConversionsService conversionsService;
    private final CurrenciesService currenciesService;

    @GetMapping("/conversions")
    public String showConversionsList(
            Model model,
            @ModelAttribute ConversionsFilterModel filter,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNum,
            Authentication auth) {
        //Получение и запись в модель данных фильтров
        List<Currency> currencies = currenciesService.findAllCurrencies();
        model.addAttribute("currencies", currencies);
        model.addAttribute("filter", filter);

        //Получение данных пагинации
        pageNum = pageNum < 0 ? 0 : pageNum - 1;
        Pageable pageParams = PageRequest.of(pageNum, itemsPerPage, Sort.Direction.DESC, "date");

        //Получение и запись в модель списка конвертаций
        Page<Conversion> conversionsPage = conversionsService.findUserConversions(((User) auth.getPrincipal()), filter, pageParams);
        model.addAttribute("conversionsList", conversionsPage.getContent());


        //Запись в модель данных пагинации
        PaginationModel pagination = new PaginationModel(conversionsPage.getTotalPages(), pageNum);
        model.addAttribute("pagination", pagination);
        model.addAttribute("itemsCount", conversionsPage.getTotalElements());

        return "conversions";
    }

}
