package com.konovalov.converter.entity;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "currency")
public class Currency implements Comparable<Currency> {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "num_code")
    private Integer numCode;

    @Column(name = "char_code")
    private String charCode;

    public Currency() {
    }

    public Currency(String id, String name, Integer numCode, String charCode) {
        this.id = id;
        this.name = name;
        this.numCode = numCode;
        this.charCode = charCode;
    }

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

    @Override
    public String toString() {
        return charCode + " (" + name + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Currency currency = (Currency) o;
        return Objects.equals(name, currency.name) &&
                Objects.equals(numCode, currency.numCode) &&
                Objects.equals(charCode, currency.charCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, numCode, charCode);
    }

    @Override
    public int compareTo(@NotNull Currency o) {
        int result = this.charCode.compareTo(o.charCode);
        if (result == 0) result = this.name.compareTo(o.name);
        if (result == 0) result = this.numCode.compareTo(o.numCode);
        return result;
    }
}
