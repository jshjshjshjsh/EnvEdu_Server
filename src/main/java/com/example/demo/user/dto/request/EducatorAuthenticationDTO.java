package com.example.demo.user.dto.request;

import com.example.demo.user.model.enumerate.IsAuthorized;
import lombok.Getter;

@Getter
public class EducatorAuthenticationDTO {
    private Long id;
    private IsAuthorized isAuthorized;
}
