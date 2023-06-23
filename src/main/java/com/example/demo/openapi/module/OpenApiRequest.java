package com.example.demo.openapi.module;

import com.example.demo.openapi.dto.OpenApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OpenApiRequest {

    public ResponseEntity<String> call(OpenApiParam apiParam){
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(apiParam.getDomain());

        for (int i = 0; i < apiParam.getKey().length; i++) {
            uriComponentsBuilder.queryParam(apiParam.getKey()[i], apiParam.getValue()[i]);
        }

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(uriComponentsBuilder.build(true).toUri(), String.class);

        return response;
        //int statusCodeValue = response.getStatusCodeValue();
    }
}
