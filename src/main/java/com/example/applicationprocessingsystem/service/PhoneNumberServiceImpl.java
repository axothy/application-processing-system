package com.example.applicationprocessingsystem.service;

import com.example.applicationprocessingsystem.model.db.PhoneNumber;
import com.example.applicationprocessingsystem.model.dto.PhoneNumberDto;
import com.example.applicationprocessingsystem.web.PhoneNumberFeignClient;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Extracts phone number city code, country code and number from Dadata
 */
@Service
public class PhoneNumberServiceImpl implements PhoneNumberService {
    @Autowired
    private PhoneNumberFeignClient feignClient;

    @Autowired
    private ModelMapper mapper;

    @Override
    public PhoneNumber getPhoneNumber(String number) {
        PhoneNumberDto response = feignClient.getPhoneNumber(
                "application/json",
                "application/json",
                "Token ffe21a9464c74f27de8a016251615bf01245d2c2",
                "3dec45649b18a5f208a6c010fe33d11af601d002",
                Collections.singletonList(number)).get(0);
        PhoneNumber phoneNumber = mapper.map(response, PhoneNumber.class);

        return phoneNumber;
    }
}
