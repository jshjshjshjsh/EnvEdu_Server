package com.example.demo.seed.service;

import com.example.demo.seed.model.Seed;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SeedServiceTest {

    @InjectMocks
    private SeedService seedService;

    @Test
    void extendSeedData() {
        //given
        String MAC = "aa:bb:cc:dd:ee:Ff";
        Seed seed1 = new Seed("Student1", MAC, LocalDateTime.now(), 1);

        //when
        List<Seed> extendedSeedData = seedService.extendSeedData(List.of(seed1));

        //then
        Assertions.assertThat(extendedSeedData.size()).isEqualTo(3);
        Assertions.assertThat(extendedSeedData.get(1).getMac()).isEqualTo(MAC);
    }
}