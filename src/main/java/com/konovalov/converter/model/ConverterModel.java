package com.konovalov.converter.model;

import com.konovalov.converter.entity.Currency;
import lombok.Data;

import javax.validation.constraints.*;
import java.math.BigDecimal;

import java.util.List;

@Data
public class ConverterModel {

    private List<Currency> currenciesFrom;

    private List<Currency> currenciesTo;

    @NotNull(message = "Валюта не выбрана")
    private String currencyFromId;

    @NotNull(message = "Валюта не выбрана")
    private String currencyToId;

    @Digits(integer = 12, fraction = 4, message = "Слишком большое значение")
    @DecimalMin(value = "0.0", message = "Допустимы только положительные значения")
    @NotNull(message = "Значение не указано")
    private BigDecimal inputValue;

    private BigDecimal outputValue;

    private boolean hasErrors = false;

}
