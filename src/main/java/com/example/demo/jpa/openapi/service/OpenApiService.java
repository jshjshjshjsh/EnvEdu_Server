package com.example.demo.jpa.openapi.service;

import com.example.demo.jpa.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.jpa.datacontrol.datachunk.service.DataChunkService;
import com.example.demo.jpa.openapi.dto.OpenApiParam;
import com.example.demo.jpa.openapi.model.entity.AirQuality;
import com.example.demo.jpa.openapi.model.entity.OceanQuality;
import com.example.demo.jpa.openapi.module.OpenApiRequest;
import com.example.demo.jpa.user.model.entity.User;
import com.example.demo.jpa.user.repository.UserRepository;
import com.example.demo.jpa.openapi.repository.OpenApiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OpenApiService {
    private final OpenApiRequest openApiRequest;
    private final OpenApiRepository openApiRepositoryImpl;
    private final UserRepository userRepository;
    private final DataChunkService dataChunkService;

    public ResponseEntity<String> callApi(String domain, String[] key, String[] value) throws UnsupportedEncodingException {
        return openApiRequest.call(new OpenApiParam.OpenApiParamBuilder()
                .setDomain(domain)
                .setKey(key)
                .setValue(value)
                .build());
    }

    @Transactional
    public boolean deleteAirQuality(long airQualityId){
        return false;
    }

    @Transactional
    public boolean saveAirQuality(List<AirQuality> airQualities, String username, String memo) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        for (AirQuality airQuality : airQualities) {
            airQuality.setOwner(user.get());
            airQuality.updateBasicAttribute(uuid, now, memo, DataEnumTypes.AIRQUALITY);
        }

        dataChunkService.saveMyDataCompilation(uuid, DataEnumTypes.AIRQUALITY.name(), user.get(), now, airQualities.size(), memo);
        return openApiRepositoryImpl.saveAirQuality(airQualities);
    }

    @Transactional
    public boolean saveOceanQuality(List<OceanQuality> oceanQualities, String username, String memo) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        LocalDateTime now = LocalDateTime.now();
        UUID uuid = UUID.randomUUID();
        for (OceanQuality oceanQuality : oceanQualities) {
            oceanQuality.setOwner(user.get());
            oceanQuality.updateBasicAttribute(uuid, now, memo, DataEnumTypes.OCEANQUALITY);
        }
        dataChunkService.saveMyDataCompilation(uuid, DataEnumTypes.OCEANQUALITY.name(), user.get(), now, oceanQualities.size(), memo);

        return openApiRepositoryImpl.saveOceanQuality(oceanQualities);
    }

    public List<AirQuality> findMyAirQualityChunked(UUID uuid, String username){
        Optional<User> user = userRepository.findByUsername(username);
        return openApiRepositoryImpl.findAirQualityAllByUserIdAndDataUuid(uuid, user.get().getId());
    }

    public List<OceanQuality> findMyOceanQualityChunked(UUID uuid, String username){
        Optional<User> user = userRepository.findByUsername(username);
        return openApiRepositoryImpl.findOceanQualityAllByUserIdAndDataUuid(uuid, user.get().getId());
    }

    public List<AirQuality> findMyAirQuality(String username, LocalDateTime start, LocalDateTime end) throws NoSuchElementException {
        Optional<User> user = userRepository.findByUsername(username);
        List<AirQuality> result = openApiRepositoryImpl.findAirQualityAllByUserId(user.get().getId());

        //List<AirQuality> result = openApiRepositoryImpl.findAllByDataTimeBetween(start, end);

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
