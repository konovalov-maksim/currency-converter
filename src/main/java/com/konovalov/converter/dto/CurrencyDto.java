package com.konovalov.converter.dto;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
public class CurrencyDto {

    @XmlAttribute(name = "ID")
    private String id;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "ISO_Num_Code")
    private Integer numCode;

    @XmlElement(name = "ISO_Char_Code")
    private String charCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumCode() {
        return numCode;
    }

    public void setNumCode(Integer numCode) {
        this.numCode = numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public void setCharCode(String charCode) {
        this.charCode = charCode;
    }
}
