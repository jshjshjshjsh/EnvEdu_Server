package com.example.demo.boards.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class BoardDTO {
    private String title;
    private String author;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private String content;
}
