package com.example.demo.datacontrol.dataclassroom.domain.types;

import java.util.ArrayList;
import java.util.List;

public enum ClassroomStudentGrade {
    EL1("초등1"), EL2("초등2"), EL3("초등3"), EL4("초등4"), EL5("초등5"), EL6("초등6"),
    MD1("중등1"), MD2("중등2"), MD3("중등3"),
    HI1("고등1"), HI2("고등2"), HI3("고등3");

    public final String label;

    private ClassroomStudentGrade(String label) {
        this.label = label;
    }

    public static ClassroomStudentGrade getByLabel(String label) {
        for (ClassroomStudentGrade grade : values()) {
            if (grade.label.equals(label)) {
                return grade;
            }
        }
        return null; // or throw an exception if you prefer
    }

    public static List<String> getAllLabels() {
        List<String> labels = new ArrayList<>();
        for (ClassroomStudentGrade grade : values()) {
            labels.add(grade.label);
        }
        return labels;
    }
}
