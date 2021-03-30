package com.konovalov.converter.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "currency")
@Data
@AllArgsConstructor @NoArgsConstructor
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

    @Override
    public String toString() {
        return charCode + " (" + name + ")";
    }

    @Override
    public int compareTo(@NotNull Currency o) {
        int result = this.charCode.compareTo(o.charCode);
        if (result == 0) result = this.name.compareTo(o.name);
        if (result == 0) result = this.numCode.compareTo(o.numCode);
        return result;
    }
}
