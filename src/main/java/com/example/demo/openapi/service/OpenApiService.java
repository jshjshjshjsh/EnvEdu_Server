package com.example.demo.openapi.service;

import com.example.demo.openapi.dto.OpenApiParam;
import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.model.entity.OceanQuality;
import com.example.demo.openapi.module.OpenApiRequest;
import com.example.demo.openapi.repository.OpenApiRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiRequest openApiRequest;
    private final OpenApiRepository openApiRepositoryImpl;
    private final UserRepository userRepository;

    public ResponseEntity<String> callApi(String domain, String[] key, String[] value) throws UnsupportedEncodingException {
        return openApiRequest.call(new OpenApiParam.OpenApiParamBuilder()
                .setDomain(domain)
                .setKey(key)
                .setValue(value)
                .build());
    }

    @Transactional
    public boolean saveAirQuality(List<AirQuality> airQualities, String username) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        for (AirQuality airQuality : airQualities) {
            airQuality.setOwner(user.get());
        }

        return openApiRepositoryImpl.saveAirQuality(airQualities);
    }

    @Transactional
    public boolean saveOceanQuality(List<OceanQuality> oceanQualities, String username) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        for (OceanQuality oceanQuality : oceanQualities) {
            oceanQuality.setOwner(user.get());
        }

        return openApiRepositoryImpl.saveOceanQuality(oceanQualities);
    }

    public List<AirQuality> findMyAirQuality(String username) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        List<AirQuality> result = openApiRepositoryImpl.findAirQualityAllByUserId(user.get().getId());

        for (AirQuality airQuality : result) {
            airQuality.setOwner(null);
        }
        return result;
    }

    public List<OceanQuality> findMyOceanQuality(String username) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        List<OceanQuality> result = openApiRepositoryImpl.findOceanQualityAllByUserId(user.get().getId());

        for (OceanQuality oceanQuality : result) {
            oceanQuality.setOwner(null);
        }
        return result;
    }
}
