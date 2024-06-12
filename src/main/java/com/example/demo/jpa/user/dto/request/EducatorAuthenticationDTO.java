package com.example.demo.jpa.user.dto.request;

import com.example.demo.jpa.user.model.enumerate.IsAuthorized;
import lombok.Getter;

@Getter
public class EducatorAuthenticationDTO {
    private Long id;
    private IsAuthorized isAuthorized;
}
