package com.example.ludikgames.service.impl;

import com.example.ludikgames.dto.EmailValidationDto;
import com.example.ludikgames.service.RegistrationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final ObjectMapper objectMapper;

    @Override
    public boolean isEmailValid(String email) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://domain-checker7.p.rapidapi.com/whois?domain=fizullin.ayaz%40gmail.com")
                .get()
                .addHeader("X-RapidAPI-Key", "1c3857b93cmshb1d603094cd051dp1089ddjsneaa3cd795164")
                .addHeader("X-RapidAPI-Host", "domain-checker7.p.rapidapi.com")
                .build();

        try(Response response = client.newCall(request).execute()) {
            if(response.isSuccessful() && response.body() != null) {
                EmailValidationDto emailValidationDto =
                        objectMapper.readValue(response.body().string(), EmailValidationDto.class);
                return Objects.equals(emailValidationDto.getStatus(), "valid");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
