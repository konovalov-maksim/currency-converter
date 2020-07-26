package com.konovalov.converter.repository;

import com.konovalov.converter.entity.Conversion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ConversionRepository extends PagingAndSortingRepository<Conversion, Long> {
    @Query("select c from Conversion c where c.userId = #{principal.id}")
    List<Conversion> findAllForCurrentUser();
}
