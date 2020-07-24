package com.konovalov.converter.repository;

import com.konovalov.converter.entity.Conversion;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ConversionRepository extends PagingAndSortingRepository<Conversion, Long> {

}
