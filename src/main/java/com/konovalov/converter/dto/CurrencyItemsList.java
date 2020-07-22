package com.konovalov.converter.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name ="Valuta")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyItemsList {

    @XmlElement(name = "Item")
    private List<CurrencyItem> currencyItems;

    public List<CurrencyItem> getCurrencyItems() {
        return currencyItems;
    }

    public void setCurrencyItems(List<CurrencyItem> currencyItems) {
        this.currencyItems = currencyItems;
    }
}
