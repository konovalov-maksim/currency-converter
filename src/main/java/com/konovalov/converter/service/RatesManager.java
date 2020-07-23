package com.konovalov.converter.service;

import com.konovalov.converter.dto.RateDto;
import com.konovalov.converter.dto.RatesListDto;
import com.konovalov.converter.entity.Rate;
import com.konovalov.converter.repository.CurrencyRepository;
import com.konovalov.converter.repository.RateRepository;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RatesManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OkHttpClient client;
    private final RateRepository rateRepo;
    private final CurrencyRepository currencyRepository;
    private final ModelMapper mapper;

    @Value("${http.url.rates}")
    private String ratesRequestUrl;

    @Autowired
    public RatesManager(
            OkHttpClient client,
            RateRepository rateRepo,
            CurrencyRepository currencyRepository,
            ModelMapper mapper) {
        this.client = client;
        this.rateRepo = rateRepo;
        this.mapper = mapper;
        this.currencyRepository = currencyRepository;
    }

    //TODO реализовать проверку актуальности курсов

    public void updateRates() {
        Request request = new Request.Builder()
                .url(ratesRequestUrl)
                .build();
        client.newCall(request).enqueue(callback);
    }

    private final Callback callback = new Callback() {
        @Override
        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
            logger.info("Запрос на получение списка курсов валют: получен ответ " + response.code());
            processResponse(response);
        }

        @Override
        public void onFailure(@NotNull Call call, @NotNull IOException e) {
            logger.error("Неудачная попытка получения списка курсов валют", e);
        }
    };


    private void processResponse(Response response) {
        try {
            InputStream responseXml = response.body().byteStream();
            RatesListDto ratesListDto = extractRatesList(responseXml);
//            List<Rate> rates = convertToEntities(ratesListDto);
            logger.info("Получено курсов валют: " + ratesListDto.getRatesList().size());
            //TODO добавить курс рубля
            for (RateDto rateDto : ratesListDto.getRatesList()) {
                try {
                    saveRate(rateDto, ratesListDto.getDate());
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("Ошибка сохранения: курс валюты " + rateDto + " уже был сохранен ранее", e);
                }
            }
            logger.info("Обновление курсов валют завершено");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Ошибка обработки ответа по запросу на получение курсов валют", e);
        }
    }

    private RatesListDto extractRatesList(InputStream responseXml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RatesListDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (RatesListDto) unmarshaller.unmarshal(responseXml);
    }

    private List<Rate> convertToEntities(RatesListDto ratesListDto) {
        List<Rate> rates = new ArrayList<>();
        for (RateDto rateDto : ratesListDto.getRatesList()) {
            Rate rate = mapper.map(rateDto, Rate.class);
            rate.setDate(ratesListDto.getDate());
            rate.setCurrency(currencyRepository.getOne(rateDto.getCurrencyId()));
            rates.add(rate);
        }
        return rates;
    }

    @Transactional
    void saveRate(RateDto rateDto, Date rateDate) {
        Rate rate = mapper.map(rateDto, Rate.class);
        rate.setDate(rateDate);
        rate.setCurrency(currencyRepository.getOne(rateDto.getCurrencyId()));
        rateRepo.save(rate);
    }



}
