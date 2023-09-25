package com.example.demo.datacontrol.dataupload;

import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.dataupload.dto.DataUploadRequestDto;
import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.openapi.model.entity.OceanQuality;
import com.example.demo.openapi.service.OpenApiService;
import com.example.demo.seed.model.Seed;
import com.example.demo.seed.service.SeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataUploadService {

    private final OpenApiService openApiService;
    private final SeedService seedService;

    public void uploadData(DataUploadRequestDto uploadedData, String username){

        // todo: 여기 if else 문 말고 괜찮은 방법?
        if (uploadedData.getLabel().equals(DataEnumTypes.AIRQUALITY.name())) {
            List<AirQuality> data = new ArrayList<>();
            for (List<String> item : uploadedData.getData()) {
                // todo : AirQuality에 뭐 막 super로 상속하고 그런거 없이 팩토리 메소드같은거 못 쓰나?
                AirQuality airQuality = new AirQuality(LocalDateTime.parse(item.get(0), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        item.get(1), item.get(2), item.get(3), item.get(4), item.get(5), item.get(6), item.get(7));
                data.add(airQuality);
            }
            openApiService.saveAirQuality(data, username, uploadedData.getMemo());
        }
        else if (uploadedData.getLabel().equals(DataEnumTypes.OCEANQUALITY.name())) {
            List<OceanQuality> data = new ArrayList<>();
            for (List<String> item : uploadedData.getData()) {
                OceanQuality oceanQuality = new OceanQuality(item.get(0), Integer.valueOf(item.get(1)), Integer.valueOf(item.get(2)), item.get(3), item.get(4),
                        item.get(5), item.get(6), item.get(7),item.get(8), item.get(9), item.get(10), item.get(11), item.get(12),
                        item.get(13));
                data.add(oceanQuality);
            }
            openApiService.saveOceanQuality(data, username, uploadedData.getMemo());
        }
        else if (uploadedData.getLabel().equals(DataEnumTypes.SEED.name())) {
            List<Seed> data = new ArrayList<>();
            for (List<String> item : uploadedData.getData()) {
                Seed seed = new Seed(username, item.get(0), Float.parseFloat(item.get(1)), Float.parseFloat(item.get(2)),Float.parseFloat(item.get(3)),Float.parseFloat(item.get(4))
                ,Float.parseFloat(item.get(5)),Float.parseFloat(item.get(6)),Float.parseFloat(item.get(7)),Float.parseFloat(item.get(8)),Float.parseFloat(item.get(9)),Float.parseFloat(item.get(10))
                ,item.get(11), item.get(12), item.get(13), Integer.parseInt(item.get(14)));
                data.add(seed);
            }
            seedService.saveData(data, uploadedData.getMemo());
        }
    }
}
