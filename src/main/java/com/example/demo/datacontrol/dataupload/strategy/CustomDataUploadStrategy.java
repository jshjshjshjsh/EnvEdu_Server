package com.example.demo.datacontrol.dataupload.strategy;

import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.dataliteracy.model.dto.CustomDataDto;
import com.example.demo.datacontrol.dataliteracy.service.DataLiteracyService;
import com.example.demo.datacontrol.dataupload.dto.DataUploadRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomDataUploadStrategy implements DataUploadStrategy {

    private final DataLiteracyService dataLiteracyService;

    @Override
    public DataEnumTypes getSupportedType() {
        return DataEnumTypes.CUSTOM;
    }

    @Override
    public void upload(DataUploadRequestDto dto, String username) {
        CustomDataDto customDataDto = new CustomDataDto(dto.getProperties(), dto.getData(), dto.getAxisTypes(), null, null, dto.getMemo(), null,
                dto.getClassId(), dto.getChapterId(), dto.getSequenceId(), false, dto.getCanShared(), dto.getCanSubmit());
        dataLiteracyService.uploadCustomData(customDataDto, username);
    }
}