package com.konovalov.converter.service;

import com.konovalov.converter.entity.Conversion;
import com.konovalov.converter.entity.Rate;
import com.konovalov.converter.entity.User;
import com.konovalov.converter.repository.ConversionRepository;
import com.konovalov.converter.repository.RateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.DateUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    public BigDecimal convert(BigDecimal inputValue, String currencyFromId, String currencyToId) {
        try {
            //TODO исправить этот кусок, юзера определять автоматом
            User user = new User();
            user.setId(1L);
            user.setUsername("user1");

            Date today = DateUtils.createToday().getTime();
            Rate rateFrom = rateRepository.findByCurrencyIdAndDate(currencyFromId, today);
            Rate rateTo = rateRepository.findByCurrencyIdAndDate(currencyToId, today);
            Conversion conversion = new Conversion(inputValue, rateFrom, rateTo, user);
            conversionRepo.save(conversion);
            return conversion.calculateOutputValue();
        } catch (Exception e) {
            logger.error("Ошибка конвертации", e);
            //TODO подумать, может лучше пробросить исключение
            return new BigDecimal(0);
        }
    }

    public List<Conversion> findUserConversions(Long userId) {
        return conversionRepo.findAllByUserId(userId);
    }


}
