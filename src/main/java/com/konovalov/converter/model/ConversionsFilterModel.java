package com.konovalov.converter.model;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    public String getCurrencyFromId() {
        return currencyFromId;
    }

    public void setCurrencyFromId(String currencyFromId) {
        if ("0".equals(currencyFromId)) currencyFromId = null;
        this.currencyFromId = currencyFromId;
    }

    public String getCurrencyToId() {
        return currencyToId;
    }

    public void setCurrencyToId(String currencyToId) {
        if ("0".equals(currencyToId)) currencyToId = null;
        this.currencyToId = currencyToId;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

}
