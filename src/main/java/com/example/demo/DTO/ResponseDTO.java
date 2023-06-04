package com.example.demo.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Deprecated
public class ResponseDTO<T> {
    /**
     * 더 이상 사용되지 않음
     * Controller의 return은 ResponseEntity로 대체함
     */
    int code;
    T data;
}
