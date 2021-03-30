package com.konovalov.converter.repository;

import com.konovalov.converter.model.entity.Conversion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;


@Repository
public interface ConversionRepository extends PagingAndSortingRepository<Conversion, Long> {

    @Query("""
            from Conversion c 
            where c.userId = :userId 
            and (c.rateFrom.currencyId = :currencyFromId or :currencyFromId is null)
            and (c.rateTo.currencyId = :currencyToId or :currencyToId is null) 
            and (date_trunc('day', c.date) = :date or cast(:date AS date) is null)
            """)
    Page<Conversion> findConversions(
            Pageable pageRequest,
            @Param("userId") Long userId,
            @Param("currencyFromId") String currencyFromId,
            @Param("currencyToId") String currencyToId,
            @Param("date") Date date
            );
}
