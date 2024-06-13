package com.example.demo.datacontrol.datafolder.dto;

import com.example.demo.datacontrol.datafolder.model.DataFolder;
import com.example.demo.user.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DataFolderDto {

    private Long id;
    private String folderName;
    private User owner;
    private List<DataFolder> child = new ArrayList<>();
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    /** 이하부턴 DataFolder에 없는 속성 */
    private Long parentId;
    private Long childId;

    public DataFolderDto convertToDto(DataFolder dataFolder) {
        id = dataFolder.getId();
        folderName = dataFolder.getFolderName();
        owner = dataFolder.getOwner();
        child = dataFolder.getChild();
        createDate = dataFolder.getCreateDate();
        updateDate = dataFolder.getUpdateDate();

        for (DataFolder item : dataFolder.getChild()){
            item.deleteThisParentDataFolder();
        }

        return this;
    }
}
