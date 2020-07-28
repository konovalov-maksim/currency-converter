package com.konovalov.converter.dto;

import com.konovalov.converter.dto.adapter.BigDecimalAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;
import java.util.Objects;

@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
public class RateDto {

    @XmlAttribute(name = "ID")
    private String currencyId;

    @XmlElement(name = "Nominal")
    private Integer nominal;

    @XmlElement(name = "Value")
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal value;

    public RateDto() {
    }

    public RateDto(String currencyId, Integer nominal, BigDecimal value) {
        this.currencyId = currencyId;
        this.nominal = nominal;
        this.value = value;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public Integer getNominal() {
        return nominal;
    }

    public void setNominal(Integer nominal) {
        this.nominal = nominal;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return currencyId + " : " + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RateDto rateDto = (RateDto) o;
        return Objects.equals(currencyId, rateDto.currencyId) &&
                Objects.equals(nominal, rateDto.nominal) &&
                Objects.equals(value, rateDto.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyId, nominal, value);
    }
}
