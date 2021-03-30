package com.konovalov.converter.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import static java.math.RoundingMode.HALF_DOWN;

@Entity
@Table(name = "conversion")
@Data @ToString(of = {"id", "date", "inputValue", "userId"})
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", insertable = false, updatable = false)
    private Date date;

    @Column(name = "input_value")
    private BigDecimal inputValue;

    @Column(name = "user_id", insertable = false, updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rate_from_id", referencedColumnName = "id")
    private Rate rateFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rate_to_id", referencedColumnName = "id")
    private Rate rateTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Conversion() {
    }

    public Conversion(BigDecimal inputValue, Rate rateFrom, Rate rateTo, User user) {
        this.inputValue = inputValue;
        this.rateFrom = rateFrom;
        this.rateTo = rateTo;
        this.user = user;
    }

    public BigDecimal calculateOutputValue() {
        BigDecimal inputCurrencyRelationToRouble = rateFrom.getValue()
                .divide(new BigDecimal(rateFrom.getNominal()), 8, HALF_DOWN);
        BigDecimal outputCurrencyRelationToRouble = rateTo.getValue()
                .divide(new BigDecimal(rateTo.getNominal()), 8, HALF_DOWN);
        return inputValue
                .multiply(inputCurrencyRelationToRouble)
                .divide(outputCurrencyRelationToRouble, 4, HALF_DOWN);
    }

}
