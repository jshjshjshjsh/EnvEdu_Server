package com.example.demo.datacontrol.dataupload.strategy;

import com.example.demo.datacontrol.datachunk.model.parent.DataEnumTypes;
import com.example.demo.datacontrol.dataupload.dto.DataUploadRequestDto;

public interface DataUploadStrategy {
    DataEnumTypes getSupportedType();
    void upload(DataUploadRequestDto dto, String username);
}
