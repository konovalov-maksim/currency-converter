package com.konovalov.converter.service;

import com.konovalov.converter.dto.RateDto;
import com.konovalov.converter.dto.RatesListDto;
import com.konovalov.converter.entity.Rate;
import com.konovalov.converter.repository.CurrencyRepository;
import com.konovalov.converter.repository.RateRepository;
import okhttp3.*;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class RatesService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OkHttpClient client;
    private final RateRepository rateRepo;
    private final CurrencyRepository currencyRepository;
    private final ModelMapper mapper;

    private final Date today = DateUtils.createToday().getTime();

    @Value("${http.url.rates}")
    private String ratesRequestUrl;

    @Value("${data.rubId}")
    private String roubleCurrencyId;

    @Autowired
    public RatesService(
            OkHttpClient client,
            RateRepository rateRepo,
            CurrencyRepository currencyRepository,
            ModelMapper mapper) {
        this.client = client;
        this.rateRepo = rateRepo;
        this.mapper = mapper;
        this.currencyRepository = currencyRepository;
    }

    public boolean areRatesOutdated() {
        Date lastStoredRatesDate = rateRepo.findLastRatesDate();
        return lastStoredRatesDate == null || lastStoredRatesDate.before(today);
    }

    public void updateRates() {
        //Если в запросе не указывать дату, могут быть получены данные на завтрашний день
        //Задача - получить данные на сегодня, поэтому явно задаем дату
        //(При этом все равно есть вероятность получить курсы на более раннюю дату)
        String todayStr = new SimpleDateFormat("dd/MM/yyyy").format(today);
        HttpUrl requestUrl = HttpUrl.parse(ratesRequestUrl).newBuilder()
                .addQueryParameter("date_req", todayStr)
                .build();
        Request request = new Request.Builder()
                .url(requestUrl)
                .build();
        try {
            Response response = client.newCall(request).execute();
            logger.info("Запрос на получение списка курсов валют: получен ответ " + response.code());
            processResponse(response);
        } catch (IOException e) {
            logger.error("Неудачная попытка получения списка курсов валют", e);
        }
    }

    private void processResponse(Response response) {
        try {
            InputStream responseXml = response.body().byteStream();
            RatesListDto ratesListDto = extractRatesList(responseXml);
            logger.info("Получено курсов валют: " + ratesListDto.getRatesList().size());
            if (ratesListDto.getRatesList().isEmpty()) return;
            //В XML, получаемом от сервиса ЦБР, отсутствуют данные по курсу рубля. Добавляем их вручную
            //Поскольку курсы остальных валют заданы по отношению к рублю, можно быть уверенным,
            //что для рубля номинал и курс всегда будут равны 1
            ratesListDto.getRatesList().add(new RateDto(roubleCurrencyId, 1, new BigDecimal(1)));
            for (RateDto rateDto : ratesListDto.getRatesList()) {
                try {
                    saveRate(rateDto, ratesListDto.getDate());
                } catch (Exception e) {
                    logger.error("Ошибка обновления курса валюты " + rateDto, e);
                }
            }
            logger.info("Обновление курсов валют завершено");
        } catch (Exception e) {
            logger.error("Ошибка обработки ответа по запросу на получение курсов валют", e);
        }
    }

    private RatesListDto extractRatesList(InputStream responseXml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(RatesListDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (RatesListDto) unmarshaller.unmarshal(responseXml);
    }

    @Transactional
    void saveRate(RateDto rateDto, Date rateDate) {
        Rate existingRate = rateRepo.findByCurrencyIdAndDate(rateDto.getCurrencyId(), rateDate);
        if (existingRate != null) return;
        Rate newRate = mapper.map(rateDto, Rate.class);
        newRate.setDate(rateDate);
        newRate.setCurrency(currencyRepository.getOne(rateDto.getCurrencyId()));
        rateRepo.save(newRate);
    }

}
