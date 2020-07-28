package com.konovalov.converter.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

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
}
