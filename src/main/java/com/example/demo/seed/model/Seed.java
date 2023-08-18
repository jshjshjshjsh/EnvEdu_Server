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

    // 측정 소속기관
    @Nullable
    private String unit;

    // 측정 간격 설정
    @Nullable
    private Integer period;

    public void updateUsername(String username){
        this.username = username;
    }

    public void updateUnit(String unit) {this.unit = unit;}

    public void setDate(LocalDateTime measuredDate) {
        this.measuredDate = measuredDate;
    }

    public Seed(String username, String mac, LocalDateTime measuredDate, Integer period) {
        this.username = username;
        this.mac = mac;
        this.measuredDate = measuredDate;
        this.period = period;
    }
}
