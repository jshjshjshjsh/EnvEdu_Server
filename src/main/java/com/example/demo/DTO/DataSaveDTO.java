package com.example.demo.DTO;

import com.example.demo.seed.model.Seed;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSaveDTO {
    /**
     * Seed 데이터를 저장할 때 사용하는 DTO
     * todo: seed package로 이동, 사용하지 않는 어노테이션 제거
     */
    List<String> data;
}
