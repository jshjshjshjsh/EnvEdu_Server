package com.example.demo.datacontrol.dataclassroom.domain.types;

import java.util.ArrayList;
import java.util.List;

public enum ClassroomSubjectType {
    SOCIAL("사회"), ENVIRONMENT("환경"), MATH("수학"), COMPUTING("전산"), COMMON("공통"), ETC("기타"),
    INTEGRATED_SCIENCE1("통합과학1"), INTEGRATED_SCIENCE2("통합과학2"), SCIENCE_EXPERIMENT1("과학탐구실험1"), SCIENCE_EXPERIMENT2("과학탐구실험2"),
    PHYSICS("물리학"), CHEMISTRY("화학"), EARTH_SCIENCE("지구과학"), EARTH_SYSTEM_SCIENCE("지구시스템과학"), LIFE_SCIENCE("생명과학"),
    PLANETARY_SPACE_SCIENCE("행성우주과학"), CLIMATE_CHANGE_ENVIRONMENT("기후변화와 환경생태"), CONVERGENCE_SCIENCE_EXPLORATION("융합과학탐구"),
    MECHANICS_ENERGY("역학과 에너지"), MATTER_ENERGY("물질과 에너지"), CHEMICAL_REACTION("화학반응의 세계"), ELECTROMAGNETIC_QUANTUM("전자기와 양자");

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
