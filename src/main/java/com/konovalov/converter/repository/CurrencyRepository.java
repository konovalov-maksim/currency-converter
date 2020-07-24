package com.konovalov.converter.repository;

import com.konovalov.converter.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
    @Query("select r.currency from Rate r where r.date = :rateDate")
    List<Currency> findCurrenciesForDate(@Param("rateDate") Date rateDate);
}
