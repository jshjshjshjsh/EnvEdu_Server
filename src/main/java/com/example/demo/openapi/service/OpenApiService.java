package com.example.demo.openapi.service;

import com.example.demo.openapi.dto.OceanQualityDTO;
import com.example.demo.openapi.dto.OpenApiParam;
import com.example.demo.openapi.module.OpenApiRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiRequest openApiRequest;

    public ResponseEntity<String> callApi(String domain, String[] key, String[] value) throws UnsupportedEncodingException {
        return openApiRequest.call(new OpenApiParam.OpenApiParamBuilder()
                .setDomain(domain)
                .setKey(key)
                .setValue(value)
                .build());
    }


}
