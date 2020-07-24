package com.konovalov.converter.service;

import com.konovalov.converter.dto.CurrencyItemsList;
import com.konovalov.converter.entity.Currency;
import com.konovalov.converter.repository.CurrencyRepository;
import okhttp3.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;


import javax.xml.bind.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CurrenciesManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OkHttpClient client;
    private final CurrencyRepository currencyRepo;
    private final ModelMapper mapper;

    @Value("${http.url.currencies}")
    private String currenciesRequestUrl;

    @Value("${data.rubId}")
    private String roubleId;

    @Value("${data.rubName}")
    private String roubleName;

    @Value("${data.rubNumCode}")
    private int roubleNumCode;

    @Value("${data.rubCharCode}")
    private String roubleCharCode;

    @Autowired
    public CurrenciesManager(
            OkHttpClient client,
            CurrencyRepository currencyRepo,
            ModelMapper mapper) {
        this.client = client;
        this.currencyRepo = currencyRepo;
        this.mapper = mapper;
    }

    public List<Currency> findCurrenciesWithRelevantRate() {
        return currencyRepo.findCurrenciesForDate(DateUtils.createToday().getTime());
    }

    public void updateCurrencies() {
        Request request = new Request.Builder()
                .url(currenciesRequestUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            logger.info("Запрос на список валют: получен ответ " + response.code());
            processResponse(response);
        } catch (IOException e) {
            logger.error("Неудачная попытка получения списка валют", e);
        }
    }

    private void processResponse(Response response) {
        try {
            InputStream responseXml = response.body().byteStream();
            List<Currency> currencies = extractCurrencies(responseXml);
            //В XML, получаемом от сервиса ЦБР отсутствуют данные по рублю
            //Для того, чтобы в конвертер включить рубль, добавляем его вручную
            Currency roubles = new Currency(roubleId, roubleName, roubleNumCode, roubleCharCode);
            currencies.add(roubles);

            for (Currency currency : currencies) {
                try {
                    currencyRepo.save(currency);
                } catch (DataAccessException e) {
                    logger.error("Ошибка сохранения данных валюты " + currency, e);
                }
            }
            logger.info("Обновление списка валют завершено");
        } catch (JAXBException e) {
            logger.error("Ошибка парсинга списка валют", e);
        } catch (Exception e) {
            logger.error("Ошибка обработки ответа по запросу на получение списка валют", e);
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
