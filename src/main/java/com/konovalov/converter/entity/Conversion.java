package com.konovalov.converter.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

import static java.math.RoundingMode.HALF_DOWN;

@Entity
@Table(name = "conversion")
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", insertable = false, updatable = false)
    private Date date;

    @Column(name = "input_value")
    private BigDecimal inputValue;

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
                .divide(new BigDecimal(rateFrom.getNominal()), 4, HALF_DOWN);
        BigDecimal outputCurrencyRelationToRouble = rateTo.getValue()
                .divide(new BigDecimal(rateTo.getNominal()), 4, HALF_DOWN);
        return inputValue
                .multiply(inputCurrencyRelationToRouble)
                .divide(outputCurrencyRelationToRouble, 4, HALF_DOWN);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public BigDecimal getInputValue() {
        return inputValue;
    }

    public void setInputValue(BigDecimal inputValue) {
        this.inputValue = inputValue;
    }

    public Rate getRateFrom() {
        return rateFrom;
    }

    public void setRateFrom(Rate rateFrom) {
        this.rateFrom = rateFrom;
    }

    public Rate getRateTo() {
        return rateTo;
    }

    public void setRateTo(Rate rateTo) {
        this.rateTo = rateTo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Conversion{" +
                "date=" + date +
                ", inputValue=" + inputValue +
                ", rateFrom=" + rateFrom +
                ", rateTo=" + rateTo +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversion that = (Conversion) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(inputValue, that.inputValue) &&
                Objects.equals(rateFrom, that.rateFrom) &&
                Objects.equals(rateTo, that.rateTo) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, inputValue, rateFrom, rateTo, user);
    }
}
