package com.konovalov.converter.service;

import com.konovalov.converter.entity.Conversion;
import com.konovalov.converter.entity.Rate;
import com.konovalov.converter.entity.User;
import com.konovalov.converter.model.ConversionsFilterModel;
import com.konovalov.converter.repository.ConversionRepository;
import com.konovalov.converter.repository.RateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConversionsService {

    private final ConversionRepository conversionRepo;
    private final RateRepository rateRepository;

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
            log.error("Ошибка конвертации", e);
            throw e;
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
