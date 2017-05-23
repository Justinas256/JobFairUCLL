/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author justinas
 */

@Entity
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;
    
    @Column
    private byte[] image;
    
    @OneToMany(mappedBy = "map")
    private List<SpotData> spotsData; 

    public Map(Long id, String name, byte[] image, List<SpotData> spotData) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.spotsData = spotData;
    }
    
    public Map(String name, byte[] image, List<SpotData> spotData) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.spotsData = spotData;
    }

     public void addSpot(SpotData spot) {
        if(this.spotsData == null) {
            spotsData = new ArrayList<SpotData>();
        }
        spotsData.add(spot);
    }
    
    public void deleteSpot(Long spotDataId) {
        for(SpotData spot: spotsData) {
            if(spot.getId().equals(id)) {
                spotsData.remove(spot);
            }
        }
    }
    
    public Map() {
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public byte[] getImage() {
        return image;
    }

    public List<SpotData> getSpotsData() {
        return spotsData;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setSpotData(List<SpotData> spotsData) {
        this.spotsData = spotsData;
    }
}
