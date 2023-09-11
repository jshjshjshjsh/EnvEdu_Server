package com.example.demo.datacontrol.datafolder.model;

import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DataFolder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String folderName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private DataFolder parent;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private List<DataFolder> child = new ArrayList<>();

    public void updateDataFolder(String folderName, User owner) {
        this.folderName = folderName;
        this.owner = owner;
    }
    public void updateParentDataFolder(DataFolder inputParentDataFolder) {
        parent = inputParentDataFolder;
    }
    public void deleteThisParentDataFolder(){
        deleteParentDFS(this);
    }
    private boolean deleteParentDFS(DataFolder inputDataFolder){
        inputDataFolder.deleteParent();

        if (inputDataFolder.getChild().isEmpty())
            return true;

        for(DataFolder item:inputDataFolder.getChild()){
            deleteParentDFS(item);
        }
        return true;
    }
    private void deleteParent(){
        this.parent = null;
    }

}
