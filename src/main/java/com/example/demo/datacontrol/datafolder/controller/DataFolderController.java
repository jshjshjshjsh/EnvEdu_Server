package com.example.demo.datacontrol.datafolder.controller;

import com.example.demo.datacontrol.datafolder.dto.DataFolderDto;
import com.example.demo.datacontrol.datafolder.dto.DataFolder_DataCompilationDto;
import com.example.demo.datacontrol.datafolder.service.DataFolderService;
import com.example.demo.datacontrol.datafolder.model.DataFolder;
import com.example.demo.datacontrol.datafolder.model.DataFolder_DataCompilation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DataFolderController {

    private final DataFolderService dataFolderService;

    @GetMapping("/datafolder/items")
    public ResponseEntity<?> getDataFolderCompilation(@RequestParam Long id){
        DataFolder_DataCompilationDto byDataFolder = dataFolderService.findByDataFolderCompilationId(id);

        return new ResponseEntity<>(byDataFolder, HttpStatus.OK);
    }
    @GetMapping("/datafolder/list")
    public ResponseEntity<?> getDataFolder(@RequestParam Long id){
        List<DataFolder> byDataFolder = dataFolderService.findByDataFolder(id);

        return new ResponseEntity<>(byDataFolder, HttpStatus.OK);
    }
}
