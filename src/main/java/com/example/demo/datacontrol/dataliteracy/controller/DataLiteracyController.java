package com.example.demo.datacontrol.dataliteracy.controller;

import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.model.entity.CustomData;
import com.example.demo.datacontrol.dataliteracy.service.DataLiteracyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class DataLiteracyController {

    private final DataLiteracyService dataLiteracyService;

    @GetMapping("/dataLiteracy/customData/list")
    public ResponseEntity<?> getCustomDataList(){
        return new ResponseEntity<>(dataLiteracyService.getCustomDataList(), HttpStatus.OK);
    }

    @GetMapping("/dataLiteracy/customData/download/{uuid}")
    public ResponseEntity<?> downloadCustomData(@PathVariable UUID uuid){
        return new ResponseEntity<>(dataLiteracyService.downloadCustomData(uuid), HttpStatus.OK);
    }

    @PostMapping("/dataLiteracy/customData/upload")
    public ResponseEntity<?> uploadCustomData(@RequestBody CustomDataDto customDataDto){
        dataLiteracyService.uploadCustomData(customDataDto);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
