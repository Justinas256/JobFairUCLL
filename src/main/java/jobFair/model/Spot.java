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
import javax.persistence.OneToOne;

/**
 *
 * @author justinas
 */
@Entity
public class Spot implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column 
    private String spotNo;

    @Column
    private int tables;
    
    @Column
    private int chairs;
    
    @Column
    private boolean electricity;
    
    @Column
    private String remarks;
   
    @ManyToOne
    @JoinColumn(name = "user_spots_id")
    private Users user;
    
    @OneToOne
    @JoinColumn(name = "spotData_spots_id")
    private SpotData spotData;

    public Spot(Long id, String spotNo, int tables, int chairs, boolean electricity, String remarks, Users user, SpotData spotData) {
        this.id = id;
        this.spotNo = spotNo;
        this.tables = tables;
        this.chairs = chairs;
        this.electricity = electricity;
        this.remarks = remarks;
        this.user = user;
        this.spotData = spotData;
    }
    
    
    
    public Spot(Long id, String spotNo, int tables, int chairs, boolean electricity, String remarks, Users user) {
        this.id = id;
        this.spotNo = spotNo;
        this.tables = tables;
        this.chairs = chairs;
        this.electricity = electricity;
        this.remarks = remarks;
        this.user = user;
    }

    public Spot(String spotNo, int tables, int chairs, boolean electricity, String remarks, Users user) {
        this.spotNo = spotNo;
        this.tables = tables;
        this.chairs = chairs;
        this.electricity = electricity;
        this.remarks = remarks;
        this.user = user;
    }

    public Spot(String spotNo, SpotData spotData) {
        this.spotNo = spotNo;
        this.spotData = spotData;
    }


    public SpotData getSpotData() {
        return spotData;
    }

    public void setSpotData(SpotData spotData) {
        this.spotData = spotData;
    }
    
    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
    
    public String getSpotNo() {
        return spotNo;
    }

    public void setSpotNo(String spotNo) {
        this.spotNo = spotNo;
    }

    public Spot() {
    }

    public int getTables() {
        return tables;
    }

    public int getChairs() {
        return chairs;
    }

    public boolean isElectricity() {
        return electricity;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setTables(int tables) {
        this.tables = tables;
    }

    public void setChairs(int chairs) {
        this.chairs = chairs;
    }

    public void setElectricity(boolean electricity) {
        this.electricity = electricity;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
        if (!(object instanceof Spot)) {
            return false;
        }
        Spot other = (Spot) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jobFair.model.Spot[ id=" + id + " ]";
    }
    
}
