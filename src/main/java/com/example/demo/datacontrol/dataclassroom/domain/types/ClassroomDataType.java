package com.example.demo.datacontrol.dataclassroom.domain.types;

import java.util.ArrayList;
import java.util.List;

public enum ClassroomDataType {
    SEED("SEED"), OPENAPI("OPEN API"), SCHOOLBOOK("교과서"), CUSTOM("기타");


    public final String label;

    private ClassroomDataType(String label) {
        this.label = label;
    }

    public static ClassroomDataType getByLabel(String label) {
        for (ClassroomDataType dataType : values()) {
            if (dataType.label.equals(label)) {
                return dataType;
            }
        }
        return null; // or throw an exception if you prefer
    }

    public static List<String> getAllLabels() {
        List<String> labels = new ArrayList<>();
        for (ClassroomDataType dataType : values()) {
            labels.add(dataType.label);
        }
        return labels;
    }
}
