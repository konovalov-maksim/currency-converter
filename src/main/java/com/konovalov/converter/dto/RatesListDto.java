package com.konovalov.converter.dto;

import com.konovalov.converter.dto.adapter.DateAdapter;
import lombok.Data;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name ="ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class RatesListDto {

    @XmlElement(name = "Valute")
    private List<RateDto> ratesList;

    @XmlAttribute(name = "Date")
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date date;

}
