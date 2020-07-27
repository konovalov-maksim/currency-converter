package com.konovalov.converter.service;

import com.konovalov.converter.entity.Conversion;
import com.konovalov.converter.entity.Rate;
import com.konovalov.converter.entity.User;
import com.konovalov.converter.model.ConversionsFilterModel;
import com.konovalov.converter.repository.ConversionRepository;
import com.konovalov.converter.repository.RateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Service
public class ConversionsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ConversionRepository conversionRepo;
    private final RateRepository rateRepository;

    @Autowired
    public ConversionsService(ConversionRepository conversionRepo, RateRepository rateRepository) {
        this.conversionRepo = conversionRepo;
        this.rateRepository = rateRepository;
    }

    @Transactional
    public BigDecimal convert(BigDecimal inputValue, String currencyFromId, String currencyToId, User user) {
        try {
            Date lastRatesDate = rateRepository.findLastRatesDate();
            Rate rateFrom = rateRepository.findByCurrencyIdAndDate(currencyFromId, lastRatesDate);
            Rate rateTo = rateRepository.findByCurrencyIdAndDate(currencyToId, lastRatesDate);
            Conversion conversion = new Conversion(inputValue, rateFrom, rateTo, user);
            conversionRepo.save(conversion);
            return conversion.calculateOutputValue();
        } catch (Exception e) {
            logger.error("Ошибка конвертации", e);
            //TODO подумать, может лучше пробросить исключение
            return new BigDecimal(0);
        }
    }

    public Page<Conversion> findUserConversions(User user, ConversionsFilterModel filter, Pageable pageParams) {
        return conversionRepo.findConversions(
                pageParams,
                user.getId(),
                filter.getCurrencyFromId(),
                filter.getCurrencyToId(),
                filter.getDate());
    }


}
