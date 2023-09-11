package com.example.demo.seed.model;

import com.example.demo.seed.dto.DeleteSeedDto;
import com.example.demo.seed.misc.Misc;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.lang.reflect.Field;
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

    public boolean deleteSingleFactor(DeleteSeedDto inputData) throws IllegalAccessException, NoSuchFieldException {
        Class<?> seedClass = this.getClass();

        for (String field: inputData.getSensors()){
            Field declaredField = seedClass.getDeclaredField(field);
            declaredField.set(this, -99999);
        }

        String[] split = toString().split(", ");

        for (int i = 0; i < split.length; i++) {
            String[] splited = split[i].split("=");
            if(!splited[1].equals("-99999.0")){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return
                "hum=" + hum +
                ", temp=" + temp +
                ", tur=" + tur +
                ", ph=" + ph +
                ", dust=" + dust +
                ", dox=" + dox +
                ", co2=" + co2 +
                ", lux=" + lux +
                ", hum_EARTH=" + hum_EARTH +
                ", pre=" + pre;
    }

    public Seed(String username, String mac, float hum, float temp, float tur, float ph, float dust, float dox, float co2, float lux, float hum_EARTH, float pre, String dateString, @Nullable String location, @Nullable String unit, @Nullable Integer period) {
        this.username = username;
        this.mac = mac;
        this.hum = hum;
        this.temp = temp;
        this.tur = tur;
        this.ph = ph;
        this.dust = dust;
        this.dox = dox;
        this.co2 = co2;
        this.lux = lux;
        this.hum_EARTH = hum_EARTH;
        this.pre = pre;
        this.dateString = dateString;
        this.location = location;
        this.unit = unit;
        this.period = period;
    }
}
