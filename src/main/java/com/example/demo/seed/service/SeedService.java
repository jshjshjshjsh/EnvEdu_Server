package com.example.demo.seed.service;

import com.example.demo.seed.model.Seed;
import com.example.demo.seed.repository.SeedRepository;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SeedService {
    private final SeedRepository seedRepository;
    private final UserRepository userRepository;

    public List<Seed> refactorSeedData(List<Seed> seeds){
        List<Seed> result = new ArrayList<>();

        int passCnt = 0;
        int passBase = 0;

        for (Seed seed : seeds) {
            if (passBase == passCnt){
                result.add(seed);
                passBase = 3;
                if (seed.getPeriod() != null)
                    passBase = seed.getPeriod();
                passCnt = 0;
            }
            passCnt ++;
        }


        return result;
    }

    /*
    * Seed Data를 3초에서 1초 간격으로 늘림
    * */
    public List<Seed> extendSeedData(List<Seed> seeds){
        List<Seed> result = new ArrayList<>();

        for (Seed seed : seeds) {
            for (int j = 0; j < 3; j++) {
                result.add(seed);
            }
        }

        return result;
    }

    @Transactional(readOnly = true)
    public List<Seed> getDataByDateAndUsername(LocalDateTime start, LocalDateTime end, String username)
    {

        if(username.equals(""))
        {
            return seedRepository.findAllByMeasuredDateBetween(start, end);
        }
        else
        {

            List<String> MacList = new ArrayList<>();
            userRepository.findByUsername(username).orElseThrow(()-> {throw new IllegalArgumentException();}).getDevices().forEach(elem -> {
                MacList.add(elem.getMac());
            });
            return seedRepository.findAllByMeasuredDateBetweenAndMacIn(start, end, MacList);
        }
    }

    @Transactional
    public void saveData(List<Seed> list)
    {
        seedRepository.saveAll(list);
    }

    @Transactional
    public void saveSingleData(Seed seed, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(value -> {
            assert value.getMeasuredUnit() != null;
            seed.updateUnit(value.getMeasuredUnit().getUnit());
        });
        seedRepository.save(seed);
    }
}
