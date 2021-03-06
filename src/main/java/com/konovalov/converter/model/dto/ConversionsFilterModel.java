package com.konovalov.converter.model.dto;

import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class ConversionsFilterModel {

    private final static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

    private String currencyFromId;

    private String currencyToId;

    private String dateStr;

    public ConversionsFilterModel(String currencyFromId, String currencyToId, String dateStr) {
        this.currencyFromId = currencyFromId;
        this.currencyToId = currencyToId;
        this.dateStr = dateStr;
    }

    public Date getDate() {
        if (dateStr == null || dateStr.isEmpty()) return null;
        try {
            return format.parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    public void setCurrencyFromId(String currencyFromId) {
        if ("0".equals(currencyFromId)) currencyFromId = null;
        this.currencyFromId = currencyFromId;
    }

    public void setCurrencyToId(String currencyToId) {
        if ("0".equals(currencyToId)) currencyToId = null;
        this.currencyToId = currencyToId;
    }

}
