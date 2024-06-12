package com.example.demo.jpa.datacontrol.dataliteracy.controller;

import com.example.demo.jpa.datacontrol.dataliteracy.model.dto.DataLiteracyDatasetDto;
import com.example.demo.jpa.datacontrol.dataliteracy.service.DataLiteracyDatasetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class DataLiteracyDatasetController {

    private final DataLiteracyDatasetService dataLiteracyDatasetService;

    @DeleteMapping("/dataset/manage")
    public ResponseEntity<?> deleteDataset(@RequestParam Long id){
        dataLiteracyDatasetService.deleteDataset(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/dataset/manage/upload")
    public ResponseEntity<?> uploadDataset(@RequestBody DataLiteracyDatasetDto dataLiteracyDatasetDto){
        dataLiteracyDatasetService.dataSetUpload(dataLiteracyDatasetDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/dataset/list")
    public ResponseEntity<?> getDatasetList(@RequestParam(required = false) String grade, @RequestParam(required = false) String subject,
                                            @RequestParam(required = false) String dataType){
        return new ResponseEntity<>(dataLiteracyDatasetService.getDatasetList(grade, subject, dataType), HttpStatus.OK);
    }
}
