package com.konovalov.converter.model.jaxb;

import com.konovalov.converter.adapter.BigDecimalAdapter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.math.BigDecimal;

@XmlRootElement(name = "Valute")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
@NoArgsConstructor @AllArgsConstructor
public class RateDto {

    @XmlAttribute(name = "ID")
    private String currencyId;

    @XmlElement(name = "Nominal")
    private Integer nominal;

    @XmlElement(name = "Value")
    @XmlJavaTypeAdapter(BigDecimalAdapter.class)
    private BigDecimal value;

}
