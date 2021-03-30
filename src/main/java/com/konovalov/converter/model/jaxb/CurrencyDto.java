package com.konovalov.converter.model.jaxb;

import lombok.Data;

import javax.xml.bind.annotation.*;

@XmlRootElement(name = "Item")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class CurrencyDto {

    @XmlAttribute(name = "ID")
    private String id;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "ISO_Num_Code")
    private Integer numCode;

    @XmlElement(name = "ISO_Char_Code")
    private String charCode;

}
