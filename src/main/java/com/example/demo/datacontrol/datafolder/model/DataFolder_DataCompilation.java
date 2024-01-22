package com.example.demo.datacontrol.datafolder.model;

import com.example.demo.datacontrol.datachunk.model.parent.DataSuperTypes;
import lombok.Getter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Getter
public class DataFolder_DataCompilation extends DataSuperTypes {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private DataFolder dataFolder;

    public void addDataFolder(DataFolder inputDataFolder) {
        dataFolder = inputDataFolder;
    }

}
