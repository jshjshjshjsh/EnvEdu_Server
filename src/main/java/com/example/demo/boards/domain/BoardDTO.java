package com.example.demo.boards.domain;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
public class BoardDTO {
    private String title;
    private String author;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String content;
}
