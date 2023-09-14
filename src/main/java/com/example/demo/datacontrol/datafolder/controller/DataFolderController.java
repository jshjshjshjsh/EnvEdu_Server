package com.example.demo.datacontrol.datafolder.controller;

import com.example.demo.datacontrol.datafolder.dto.DataFolderDto;
import com.example.demo.datacontrol.datafolder.dto.DataFolder_DataCompilationDto;
import com.example.demo.datacontrol.datafolder.service.DataFolderService;
import com.example.demo.datacontrol.datafolder.model.DataFolder;
import com.example.demo.datacontrol.datafolder.model.DataFolder_DataCompilation;
import com.example.demo.jwt.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<?> getDataFolder(HttpServletRequest request){
        Map<String, Object> userInfo = JwtUtil.getJwtRefreshTokenFromCookieAndParse(request.getCookies()).get(JwtUtil.claimName).asMap();
        List<DataFolder> byDataFolder = dataFolderService.findByDataFolder(userInfo.get(JwtUtil.claimUsername).toString());

        return new ResponseEntity<>(byDataFolder, HttpStatus.OK);
    }
}
