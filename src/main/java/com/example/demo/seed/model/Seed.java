package com.example.demo.seed.model;

import com.example.demo.seed.misc.Misc;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 20)
    private String mac;

    @Nullable
    @Builder.Default
    private float hum = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float temp = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float tur = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float ph = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float dust = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float dox = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float co2 = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float lux = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float hum_EARTH = Misc.SeedDefaultValue;

    @Nullable
    @Builder.Default
    private float pre = Misc.SeedDefaultValue;

    private LocalDateTime measuredDate;

    @Transient
    private String dateString;

    @Nullable
    @Column(length = 50)
    private String location;

}
