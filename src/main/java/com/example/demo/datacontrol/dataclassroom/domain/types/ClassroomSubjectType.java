package com.example.demo.datacontrol.dataclassroom.domain.types;

import java.util.ArrayList;
import java.util.List;

public enum ClassroomSubjectType {
    SOCIAL("사회"), MATHANDSCIENCE("수학/과학"), INFOMATIONANDCOMPUTING("정보/전산"), ETC("기타");

    public final String label;

    private ClassroomSubjectType(String label) {
        this.label = label;
    }

    public static ClassroomSubjectType getByLabel(String label) {
        for (ClassroomSubjectType subject : values()) {
            if (subject.label.equals(label)) {
                return subject;
            }
        }
        return null; // or throw an exception if you prefer
    }

    public static List<String> getAllLabels() {
        List<String> labels = new ArrayList<>();
        for (ClassroomSubjectType subject : values()) {
            labels.add(subject.label);
        }
        return labels;
    }
}
