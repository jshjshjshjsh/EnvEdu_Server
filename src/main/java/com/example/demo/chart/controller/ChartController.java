package com.example.demo.chart.controller;

import com.example.demo.chart.service.ChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ChartController {

    private final ChartService chartService;

    @PostMapping("/chart")
    public String generateChart(@RequestBody String jsonData, Model model){
        log.info("jsonData.toString() = " + jsonData.toString());
        model.addAttribute("values", chartService.parseDataFromArray(jsonData));
        return "PopupChart";
    }
/*
    // 엑셀을 읽어와 차트 생성 컨트롤러
    @PostMapping("/chart/excel")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        log.info("file.toString() = " + file.toString());
        try {
            // Load the Excel workbook
            Workbook workbook = new XSSFWorkbook(file.getInputStream());

            // Get the first sheet in the workbook
            Sheet sheet = workbook.getSheetAt(0);

            // Iterate over rows
            for (Row row : sheet) {
                // Iterate over cells
                for (Cell cell : row) {

                    switch (row.getRowNum()){
                        case 0:
                            // Read cell value
                            String cellValue = cell.getStringCellValue();
                            // Do something with the cell value
                            System.out.println(cellValue);
                            break;
                        default:
                            // Read cell value
                            double cellValue2 = cell.getNumericCellValue();
                            // Do something with the cell value
                            System.out.println(cellValue2);
                            break;
                    }
                }
            }

            // Close the workbook
            workbook.close();

        } catch (Exception e) {
            // Handle any exceptions that occur during file reading
            e.printStackTrace();
        }
        return "SampleChart";
    }

 */


}
