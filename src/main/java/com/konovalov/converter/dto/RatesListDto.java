package com.konovalov.converter.dto;

import com.konovalov.converter.dto.adapter.DateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;

@XmlRootElement(name ="ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class RatesListDto {

    @XmlElement(name = "Valute")
    private List<RateDto> ratesList;

    @XmlAttribute(name = "Date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date;

    public List<RateDto> getRatesList() {
        return ratesList;
    }

    public void setRatesList(List<RateDto> ratesList) {
        this.ratesList = ratesList;
    }

    public Date  getDate() {
        return date;
    }

    public void setDate(Date  date) {
        this.date = date;
    }
}
