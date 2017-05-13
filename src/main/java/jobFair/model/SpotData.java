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
    
    @Column
    private String shape;
    
    @Column
    private String location;

    public SpotData(Long id, String coords, String shape, String location) {
        this.id = id;
        this.coords = coords;
        this.shape = shape;
        this.location = location;
    }

    public SpotData() {
    }

    public String getCoords() {
        return coords;
    }

    public String getShape() {
        return shape;
    }

    public String getLocation() {
        return location;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public void setLocation(String location) {
        this.location = location;
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
