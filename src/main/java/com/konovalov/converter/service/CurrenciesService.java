package com.konovalov.converter.service;

import com.konovalov.converter.dto.CurrenciesListDto;
import com.konovalov.converter.entity.Currency;
import com.konovalov.converter.repository.CurrencyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import javax.xml.bind.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CurrenciesService {

    private final OkHttpClient client;
    private final CurrencyRepository currencyRepo;
    private final ModelMapper mapper;

    @Value("${http.url.currencies}")
    private String currenciesRequestUrl;

    public List<Currency> findCurrenciesWithRelevantRate() {
        return currencyRepo.findCurrenciesWithRelevantRates();
    }

    public List<Currency> findAllCurrencies() {
        return currencyRepo.findAll(Sort.by(Sort.Direction.ASC, "charCode"));
    }

    public void updateCurrencies() {
        Request request = new Request.Builder()
                .url(currenciesRequestUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            log.info("Запрос на список валют: получен ответ " + response.code());
            processResponse(response);
        } catch (IOException e) {
            log.error("Неудачная попытка получения списка валют", e);
        }
    }

    private void processResponse(Response response) {
        try {
            InputStream responseXml = response.body().byteStream();
            List<Currency> currencies = extractCurrencies(responseXml);
            for (Currency currency : currencies) {
                try {
                    currencyRepo.save(currency);
                } catch (DataAccessException e) {
                    log.error("Ошибка сохранения данных валюты " + currency, e);
                }
            }
            log.info("Обновление списка валют завершено");
        } catch (JAXBException e) {
            log.error("Ошибка парсинга списка валют", e);
        } catch (Exception e) {
            log.error("Ошибка обработки ответа по запросу на получение списка валют", e);
        }
    }

    private List<Currency> extractCurrencies(InputStream responseXml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CurrenciesListDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        CurrenciesListDto currenciesListDto = (CurrenciesListDto) unmarshaller.unmarshal(responseXml);
        log.info("Найдено валют: " + currenciesListDto.getCurrencyDtos().size());
        return currenciesListDto.getCurrencyDtos().stream()
                .filter(item -> !item.getCharCode().isEmpty()) //Валюты без ISO кода - устаревшие, не сохраняем их
                .map(item -> mapper.map(item, Currency.class))
                .collect(Collectors.toList());
    }



}
