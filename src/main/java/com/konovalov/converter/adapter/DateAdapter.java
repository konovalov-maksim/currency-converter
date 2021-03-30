package com.konovalov.converter.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateAdapter extends XmlAdapter<String, Date> {

    private final SimpleDateFormat FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public String marshal(Date date) throws Exception {
        if (date!= null) return FORMAT.format(date);
        return null;
    }

    @Override
    public Date unmarshal(String dateStr) throws Exception {
        return FORMAT.parse(dateStr);
    }
}
