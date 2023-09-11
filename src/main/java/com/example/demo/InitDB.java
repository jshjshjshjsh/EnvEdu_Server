package com.example.demo;

import com.example.demo.admin.cipher.AdminCipher;
import com.example.demo.admin.model.Admin;
import com.example.demo.datacontrol.datafolder.model.DataFolder;
import com.example.demo.datacontrol.datafolder.model.DataFolder_DataCompilation;
import com.example.demo.datacontrol.datafolder.service.DataFolderService;
import com.example.demo.device.dto.request.AddMACDTO;
import com.example.demo.device.service.UserDeviceService;
import com.example.demo.datacontrol.datachunk.model.MeasuredUnit;
import com.example.demo.datacontrol.datachunk.service.EducatingService;
import com.example.demo.openapi.model.entity.AirQuality;
import com.example.demo.seed.model.Seed;
import com.example.demo.seed.service.SeedService;
import com.example.demo.user.dto.request.RegisterDTO;
import com.example.demo.user.model.entity.Educator;
import com.example.demo.user.model.entity.Student;
import com.example.demo.user.model.entity.Student_Educator;
import com.example.demo.user.model.entity.User;
import com.example.demo.user.model.enumerate.Gender;
import com.example.demo.user.model.enumerate.Role;
import com.example.demo.user.repository.Student_EducatorRepository;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class InitDB implements CommandLineRunner {

    @PersistenceContext
    private final EntityManager em;
    private final AdminCipher adminCipher;
    private final UserService userService;
    private final UserRepository userRepository;
    private final UserDeviceService userDeviceService;
    private final Student_EducatorRepository studentEducatorRepository;
    private final EducatingService educatingService;
    private final SeedService seedService;
    private final DataFolderService dataFolderService;

    @Override
    public void run(String... args) throws Exception {
        init();
    }

    public void init() {
        log.info("InitSampleDB.init");

        Admin admin = new Admin("admin", adminCipher.encrypt("1234"));
        em.persist(admin);

        userService.addUser(new RegisterDTO("Student1", "1234", "student1@google.com",
                Gender.MALE, Role.ROLE_STUDENT,Date.valueOf("1999-04-21")));

        userService.addUser(new RegisterDTO("Student2", "1234", "student2@google.com",
                Gender.MALE, Role.ROLE_STUDENT,Date.valueOf("1999-04-21")));

        userService.addUser(new RegisterDTO("Educator1", "1234", "educator1@google.com",
                Gender.FEMALE, Role.ROLE_EDUCATOR,Date.valueOf("1999-04-21")));

        Optional<User> student1 = userRepository.findByUsername("Student1");
        Optional<User> student2 = userRepository.findByUsername("Student2");
        Optional<User> student3 = userRepository.findByUsername("Student2");

        List<String> students = new ArrayList<>();
        students.add("Student1");
        students.add("Student2");
        Optional<User> educator1 = userRepository.findByUsername("Educator1");

        Student_Educator studentEducator = new Student_Educator().of((Student) student1.get(),(Educator) educator1.get());
        Student_Educator studentEducator2 = new Student_Educator().of((Student) student2.get(),(Educator) educator1.get());
        studentEducatorRepository.save(studentEducator);
        studentEducatorRepository.save(studentEducator2);



        AddMACDTO addMACDTO = new AddMACDTO();
        addMACDTO.setMacs(new ArrayList<>());
        List<String> macs = addMACDTO.getMacs();
        macs.add("aa:bb:cc:dd:ee:Ff");
        macs.add("aa:bb:cc:dd:FF:Ff");
        macs.add("C0:49:EF:F0:09:6C");

        List<User> users = new ArrayList<>();
        users.add(student1.get());
        users.add(student2.get());
        users.add(student3.get());

        userDeviceService.addDevice(addMACDTO, users);

        MeasuredUnit measuredUnit = new MeasuredUnit("부산대학교", "1차시", LocalDateTime.now());
        educatingService.saveMeasuredUnit(measuredUnit);

        userService.updateAllMeasuredUnit("Educator1",measuredUnit);

        Seed aa = new Seed("Student1", "aa:bb:cc:dd:ee:Ff", LocalDateTime.now(), 1);
        Seed bb = new Seed("Student1", "aa:bb:cc:dd:ee:Ff", LocalDateTime.now(), 2);
        Seed cc = new Seed("Student1", "aa:bb:cc:dd:ee:Ff", LocalDateTime.now(), 3);
        List<Seed> seeds = new ArrayList<>();
        seeds.add(aa);
        seeds.add(bb);
        seeds.add(cc);
        seedService.saveData(seeds);

        AirQuality airQuality = new AirQuality();

        DataFolder dataFolder1 = new DataFolder();
        dataFolder1.updateDataFolder("dataFolder1", student1.get());
        DataFolder dataFolder2 = new DataFolder();
        dataFolder2.updateDataFolder("dataFolder2", student1.get());
        DataFolder dataFolder3 = new DataFolder();
        dataFolder3.updateDataFolder("dataFolder3", student1.get());
        DataFolder dataFolder4 = new DataFolder();
        dataFolder4.updateDataFolder("dataFolder4", student1.get());

        dataFolderService.saveDataFolder(dataFolder1);
        dataFolderService.saveDataFolder(dataFolder2);
        dataFolderService.saveDataFolder(dataFolder3);
        dataFolderService.saveDataFolder(dataFolder4);

        dataFolder2.updateParentDataFolder(dataFolder1);
        dataFolder3.updateParentDataFolder(dataFolder2);
        dataFolder4.updateParentDataFolder(dataFolder3);

        DataFolder_DataCompilation dataFolderDataCompilation1 = new DataFolder_DataCompilation();
        dataFolderDataCompilation1.addDataFolder(dataFolder1);
        dataFolderDataCompilation1.addSeed(bb);
        dataFolderService.saveDataCompilation(dataFolderDataCompilation1);

        DataFolder_DataCompilation dataFolderDataCompilation2 = new DataFolder_DataCompilation();
        dataFolderDataCompilation2.addDataFolder(dataFolder2);
        dataFolderDataCompilation2.addSeed(bb);
        dataFolderService.saveDataCompilation(dataFolderDataCompilation2);

        DataFolder_DataCompilation dataFolderDataCompilation3 = new DataFolder_DataCompilation();
        dataFolderDataCompilation3.addDataFolder(dataFolder3);
        dataFolderDataCompilation3.addSeed(bb);
        dataFolderService.saveDataCompilation(dataFolderDataCompilation3);

        DataFolder_DataCompilation dataFolderDataCompilation4 = new DataFolder_DataCompilation();
        dataFolderDataCompilation4.addDataFolder(dataFolder4);
        dataFolderDataCompilation4.addSeed(bb);
        dataFolderService.saveDataCompilation(dataFolderDataCompilation4);

        //dataFolder1.getDataFolder_dataCompilations().add(dataFolderDataCompilation1);
    }
}