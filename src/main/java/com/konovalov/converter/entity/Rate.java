package com.konovalov.converter.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "rate")
@Data @ToString(of = {"nominal", "value", "date", "currencyId"})
public class Rate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nominal")
    private Integer nominal;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "date")
    private Date date;

    @Column(name = "currency_id", insertable = false, updatable = false)
    private String currencyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", referencedColumnName = "id")
    private Currency currency;

}
