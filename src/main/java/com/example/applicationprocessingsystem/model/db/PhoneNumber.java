package com.example.applicationprocessingsystem.model.db;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "phones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_code")
    private int countryCode;

    @Column(name = "city_code")
    private int cityCode;

    @Column(name = "number")
    private int number;

    @OneToMany(mappedBy = "phone", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Application> applications;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PhoneNumber that = (PhoneNumber) o;

        if (countryCode != that.countryCode) return false;
        if (cityCode != that.cityCode) return false;
        if (number != that.number) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + countryCode;
        result = 31 * result + cityCode;
        result = 31 * result + number;
        return result;
    }

    @Override
    public String toString() {
        return "PhoneNumber{" +
                "id=" + id +
                ", countryCode=" + countryCode +
                ", cityCode=" + cityCode +
                ", number=" + number +
                '}';
    }
}
