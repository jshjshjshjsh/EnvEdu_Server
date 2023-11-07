package com.example.demo.datacontrol.datafolder.controller;

import com.example.demo.datacontrol.datafolder.dto.DataFolderDto;
import com.example.demo.datacontrol.datafolder.dto.DataFolder_DataCompilationDto;
import com.example.demo.datacontrol.datafolder.dto.DataFromDataFolderDto;
import com.example.demo.datacontrol.datafolder.dto.DataToDataFolderDto;
import com.example.demo.datacontrol.datafolder.service.DataFolderService;
import com.example.demo.datacontrol.datafolder.model.DataFolder;
import com.example.demo.datacontrol.datafolder.model.DataFolder_DataCompilation;
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
public class DataFolderController {

    private final DataFolderService dataFolderService;

    @DeleteMapping("/datafolder/item/delete")
    public ResponseEntity<?> deleteDataFromDataFolder(@RequestBody DataFromDataFolderDto data){
        dataFolderService.delete(data.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/datafolder/item/store")
    public ResponseEntity<?> storeDataToDataFolder(@RequestBody DataToDataFolderDto dataToDataFolderDto){
        // warn : 한번의 요청에 같은 타입만 넣어야 함
        dataFolderService.store(dataToDataFolderDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/datafolder/list")
    public ResponseEntity<?> parentStoreDataFolder(HttpServletRequest request, @RequestBody Map<String, Long> folderTarget){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        dataFolderService.linkParentDataFolder(userInfo.get(JwtUtil.claimUsername).toString(), folderTarget.get("parentId"), folderTarget.get("childId"));
        //dataFolderService.linkParentDataFolder("Student1", folderTarget.get("parentId"), folderTarget.get("childId"));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/datafolder/list")
    public ResponseEntity<?> postDataFolder(HttpServletRequest request, @RequestBody Map<String, String> folderName) {
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        dataFolderService.createDataFolder(userInfo.get(JwtUtil.claimUsername).toString(), folderName.get("folderName"));
        //dataFolderService.createDataFolder("Student1", folderName.get("folderName"));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/datafolder/items")
    public ResponseEntity<?> getDataFolderCompilation(@RequestParam Long id){
        DataFolder_DataCompilationDto byDataFolder = dataFolderService.findByDataFolderCompilationId(id);

        return new ResponseEntity<>(byDataFolder, HttpStatus.OK);
    }
    @GetMapping("/datafolder/list")
    public ResponseEntity<?> getDataFolder(HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        List<DataFolder> byDataFolder = dataFolderService.findByDataFolder(userInfo.get(JwtUtil.claimUsername).toString());
//        List<DataFolder> byDataFolder = dataFolderService.findByDataFolder("Student1");

        return new ResponseEntity<>(byDataFolder, HttpStatus.OK);
    }
}
