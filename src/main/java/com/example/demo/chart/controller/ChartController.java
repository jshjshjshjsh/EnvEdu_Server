package com.example.demo.chart.controller;

import com.example.demo.chart.service.ChartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @PostMapping("/chart")
    public String generateChart(@RequestBody String jsonData, Model model){
        System.out.println("ChartController.generateChart");
        model.addAttribute("values", chartService.parseDataFromArray(jsonData));
        return "PopupChart";
    }


}
