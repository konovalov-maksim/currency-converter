package com.konovalov.converter.model;

import com.konovalov.converter.entity.Currency;

import java.math.BigDecimal;

import java.util.List;

public class ConverterModel {

    private List<Currency> currenciesFrom;

    private List<Currency> currenciesTo;

    private String currencyFromId;

    private String currencyToId;

    private BigDecimal inputValue;

    private BigDecimal outputValue;

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
}
