package com.example.demo.openapi.dto;

import lombok.Getter;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Getter
public class OpenApiParam {

    private OpenApiParam(OpenApiParamBuilder builder) {
        this.domain = builder.domain;
        this.key = builder.key;
        this.value = builder.value;
    }
    private String domain;
    private String[] key;
    private String[] value;

    public static class OpenApiParamBuilder {
        private String domain;
        private String[] key;
        private String[] value;

        public OpenApiParamBuilder setDomain(String domain) {
            this.domain = domain;
            return this;
        }

        public OpenApiParamBuilder setKey(String... keys) {
            this.key = keys;
            return this;
        }

        public OpenApiParamBuilder setValue(String... values) throws UnsupportedEncodingException {
            String[] encoded = new String[values.length];

            for (int i = 0; i < values.length; i++) {
                encoded[i] = URLEncoder.encode(values[i], StandardCharsets.UTF_8);
            }

            this.value = encoded;
            return this;
        }

        public OpenApiParam build() {
            return new OpenApiParam(this);
        }
    }
}
