package com.konovalov.converter.entities;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "conversion")
public class Conversion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "date", insertable = false)
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rate_from_id", referencedColumnName = "id")
    private Rate rateFrom;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rate_to_id", referencedColumnName = "id")
    private Rate rateTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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
                "id=" + id +
                ", date=" + date +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conversion that = (Conversion) o;
        return Objects.equals(date, that.date) &&
                Objects.equals(rateFrom, that.rateFrom) &&
                Objects.equals(rateTo, that.rateTo) &&
                Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, rateFrom, rateTo, user);
    }
}
