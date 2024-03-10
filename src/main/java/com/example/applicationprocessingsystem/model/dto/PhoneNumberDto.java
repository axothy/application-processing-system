package com.example.applicationprocessingsystem.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumberDto {
    @JsonProperty("country_code")
    private int countryCode;

    @JsonProperty("city_code")
    private int cityCode;

    @JsonProperty("number")
    private int number;
}
