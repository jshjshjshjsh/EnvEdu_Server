package com.example.demo.datacontrol.datafolder.model;

import com.example.demo.datacontrol.datachunk.model.parent.DataSuperTypes;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class DataFolder_DataCompilation extends DataSuperTypes {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private DataFolder dataFolder;

    public void addDataFolder(DataFolder inputDataFolder) {
        dataFolder = inputDataFolder;
    }

}
