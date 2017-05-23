/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author justinas
 */
@Entity
public class SpotData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String coords;
    
    @ManyToOne
    @JoinColumn(name = "map_spots_id")
    private Map map;

    public SpotData(Long id, String coords, Map map) {
        this.id = id;
        this.coords = coords;
        this.map = map;
    }
    
    public SpotData(String coords, Map map) {
        this.id = id;
        this.coords = coords;
        this.map = map;
    }

    public SpotData(Long id, String coords) {
        this.id = id;
        this.coords = coords;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    
     public SpotData(String coords, String location) {
        this.coords = coords;
    }
    
    public SpotData() {
    }

    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SpotData)) {
            return false;
        }
        SpotData other = (SpotData) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jobFair.model.SpotData[ id=" + id + " ]";
    }
    
}
