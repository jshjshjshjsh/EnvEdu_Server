package com.example.demo.boards.controller;

import com.example.demo.boards.domain.BoardDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {

    @PostMapping("/board")
    public Boolean createBoard(BoardDTO boardDTO){
        System.out.println("boardDTO.toString() = " + boardDTO.toString());

        return true;
    }
}
