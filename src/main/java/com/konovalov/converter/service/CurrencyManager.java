package com.konovalov.converter.service;

import com.konovalov.converter.dto.CurrencyItemsList;
import com.konovalov.converter.entity.Currency;
import com.konovalov.converter.repository.CurrencyRepository;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


import javax.xml.bind.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrencyManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OkHttpClient client;
    private final CurrencyRepository currencyRepo;
    private final ModelMapper mapper;

    @Value("${http.url.currencies}")
    private String currenciesRequestUrl;

    @Autowired
    public CurrencyManager(
            OkHttpClient client,
            CurrencyRepository currencyRepo,
            ModelMapper mapper) {
        this.client = client;
        this.currencyRepo = currencyRepo;
        this.mapper = mapper;
    }

    public void updateCurrencies() {
        Request request = new Request.Builder()
                .url(currenciesRequestUrl)
                .build();
        client.newCall(request).enqueue(callback);
    }

    private final Callback callback = new Callback() {
        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) {
            logger.info("Запрос на список валют: получен ответ " + response.code());
            processResponse(response);
        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            logger.error("Неудачная попытка получения списка валют", e);
        }
    };

    private void processResponse(Response response) {
        try {
            InputStream responseXml = response.body().byteStream();
            List<Currency> currencies = extractCurrencies(responseXml);
            for (Currency currency : currencies) {
                try {
                    currencyRepo.save(currency);
                } catch (DataAccessException e) {
                    e.printStackTrace();
                    logger.error("Ошибка сохранения данных валюты " + currency, e);
                }
            }
            logger.info("Обновление списка валют завершено");
        } catch (JAXBException e) {
            e.printStackTrace();
            logger.error("Ошибка парсинга списка валют", e);
        }
    }

    private List<Currency> extractCurrencies(InputStream responseXml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CurrencyItemsList.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CurrencyItemsList items = (CurrencyItemsList) unmarshaller.unmarshal(responseXml);
        logger.info("Найдено валют: " + items.getCurrencyItems().size());
        return items.getCurrencyItems().stream()
                .filter(item -> !item.getCharCode().isEmpty()) //Валюты без ISO кода - устаревшие, не сохраняем их
                .map(item -> mapper.map(item, Currency.class))
                .collect(Collectors.toList());
    }


}
