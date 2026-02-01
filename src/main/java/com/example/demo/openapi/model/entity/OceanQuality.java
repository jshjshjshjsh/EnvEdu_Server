package com.example.demo.openapi.model.entity;

import com.example.demo.openapi.model.parent.OceanQualityParent;
import com.example.demo.user.model.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "OceanQuality"
        , uniqueConstraints = @UniqueConstraint(columnNames = {"PTNM", "WMYR", "WMOD"}))
public class OceanQuality extends OceanQualityParent {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private User owner;

    public void setOwner(User owner) {
        this.owner = owner;
    }
    public OceanQuality(){
        super();
    }
    public OceanQuality(String ptNm, Integer wmyr,Integer wmod, String itemTemp,String itemPh, String itemDoc,String itemBod, String itemCod,String itemTn, String itemTp,String itemTrans, String itemCloa,String itemEc, String itemToc){
        super(ptNm, wmyr, wmod, itemTemp, itemPh, itemDoc, itemBod, itemCod, itemTn, itemTp, itemTrans, itemCloa, itemEc, itemToc);
    }
    public static OceanQuality from(List<String> data) {
        return new OceanQuality(
                data.get(0),
                Integer.valueOf(data.get(1)),
                Integer.valueOf(data.get(2)),
                data.get(3), data.get(4), data.get(5), data.get(6),
                data.get(7), data.get(8), data.get(9), data.get(10),
                data.get(11), data.get(12), data.get(13)
        );
    }
}
