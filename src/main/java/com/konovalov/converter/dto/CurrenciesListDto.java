package com.konovalov.converter.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name ="Valuta")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrenciesListDto {

    @XmlElement(name = "Item")
    private List<CurrencyDto> currencyDtos;

    public List<CurrencyDto> getCurrencyDtos() {
        return currencyDtos;
    }

    public void setCurrencyDtos(List<CurrencyDto> currencyDtos) {
        this.currencyDtos = currencyDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CurrenciesListDto that = (CurrenciesListDto) o;
        return Objects.equals(currencyDtos, that.currencyDtos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currencyDtos);
    }
}
