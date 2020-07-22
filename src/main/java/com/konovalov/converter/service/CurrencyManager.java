package com.konovalov.converter.service;

import com.konovalov.converter.dto.CurrencyItemsList;
import com.konovalov.converter.entity.Currency;
import com.konovalov.converter.repository.CurrencyRepository;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OkHttpClient client;
    private final CurrencyRepository currencyRepo;

    @Value("${http.url.currencies}")
    private String currenciesRequestUrl;

    @Autowired
    public CurrencyManager(OkHttpClient client, CurrencyRepository currencyRepo) {
        this.client = client;
        this.currencyRepo = currencyRepo;
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
            logger.error("Неудачный запрос на получение списка валют", e);
        }
    };

    private void processResponse(Response response) {
        try {
            InputStream responseXml = response.body().byteStream();
            List<Currency> currencies = extractCurrencies(responseXml);

        } catch (JAXBException e) {
            e.printStackTrace();
            logger.error("Ошибка парсинга списка валют", e);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Ошибка обработки ответа", e);
        }
    }

    private List<Currency> extractCurrencies(InputStream responseXml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(CurrencyItemsList.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        CurrencyItemsList items = (CurrencyItemsList) jaxbUnmarshaller.unmarshal(responseXml);
        logger.info("Найдено валют: " + items.getCurrencyItems().size());
        //TODO реализовать конвертацию
        return new ArrayList<>();
    }

}
