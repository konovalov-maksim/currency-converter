package com.konovalov.converter.model.jaxb;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name ="Valuta")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CurrenciesListDto {

    @XmlElement(name = "Item")
    private List<CurrencyDto> currencyDtos;

}
