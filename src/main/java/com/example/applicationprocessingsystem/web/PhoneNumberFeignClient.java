package com.example.applicationprocessingsystem.web;

import com.example.applicationprocessingsystem.model.dto.PhoneNumberDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "phone-number-service", url = "https://cleaner.dadata.ru")
public interface PhoneNumberFeignClient {
    @RequestMapping(method = RequestMethod.POST, value = "api/v1/clean/phone", consumes = "application/json")
    List<PhoneNumberDto> getPhoneNumber(
            @RequestHeader("Content-Type") String contentType,
            @RequestHeader("Accept") String accept,
            @RequestHeader("Authorization") String authorization,
            @RequestHeader("X-Secret") String secret,
            @RequestBody List<String> body);

}
