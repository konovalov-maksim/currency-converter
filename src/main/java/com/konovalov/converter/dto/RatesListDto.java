package com.konovalov.converter.dto;

import com.konovalov.converter.dto.adapter.DateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatesListDto that = (RatesListDto) o;
        return Objects.equals(ratesList, that.ratesList) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ratesList, date);
    }
}
