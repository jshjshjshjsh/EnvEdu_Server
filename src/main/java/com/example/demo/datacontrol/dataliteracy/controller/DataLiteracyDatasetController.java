package com.example.demo.datacontrol.dataliteracy.controller;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.model.dto.DataLiteracyDatasetDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.datacontrol.dataliteracy.service.DataLiteracyDatasetService;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getDatasetList(){
        return new ResponseEntity<>(dataLiteracyDatasetService.getDatasetList(), HttpStatus.OK);
    }
}
