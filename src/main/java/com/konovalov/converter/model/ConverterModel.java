package com.konovalov.converter.model;

import com.konovalov.converter.entity.Currency;

import javax.validation.constraints.*;
import java.math.BigDecimal;

import java.util.List;

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

    public List<Currency> getCurrenciesFrom() {
        return currenciesFrom;
    }

    public void setCurrenciesFrom(List<Currency> currenciesFrom) {
        this.currenciesFrom = currenciesFrom;
    }

    public List<Currency> getCurrenciesTo() {
        return currenciesTo;
    }

    public void setCurrenciesTo(List<Currency> currenciesTo) {
        this.currenciesTo = currenciesTo;
    }

    public String getCurrencyFromId() {
        return currencyFromId;
    }

    public void setCurrencyFromId(String currencyFromId) {
        this.currencyFromId = currencyFromId;
    }

    public String getCurrencyToId() {
        return currencyToId;
    }

    public void setCurrencyToId(String currencyToId) {
        this.currencyToId = currencyToId;
    }

    public BigDecimal getInputValue() {
        return inputValue;
    }

    public void setInputValue(BigDecimal inputValue) {
        this.inputValue = inputValue;
    }

    public BigDecimal getOutputValue() {
        return outputValue;
    }

    public void setOutputValue(BigDecimal outputValue) {
        this.outputValue = outputValue;
    }

    public boolean getHasErrors() {
        return hasErrors;
    }

    public void setHasErrors(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }
}
