package com.konovalov.converter.repository;

import com.konovalov.converter.entity.Rate;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface RateRepository extends CrudRepository<Rate, Long> {
    @Query("select max(r.date) from Rate r")
    Date findLastRatesDate();

    Rate findByCurrencyIdAndDate(String currencyId, Date rateDate);
}
