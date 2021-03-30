package com.konovalov.converter.repository;

import com.konovalov.converter.model.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, String> {
    @Query("select r.currency from Rate r where r.date = (select max(date) from Rate) order by r.currency.charCode")
    List<Currency> findCurrenciesWithRelevantRates();
}
