/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author justinas
 */

@Entity
public class JobFairData implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long ID;
    
    @Column
    @NotEmpty
    private String name;
    
    @Column
    @NotEmpty
    private String date;
    
    @Column
    private String location;
    
    @Column
    private Date deadline;

    public JobFairData(Long ID, String name, String date, String location, Date deadline) {
        this.ID = ID;
        this.name = name;
        this.date = date;
        this.location = location;
        this.deadline = deadline;
    }

    public JobFairData(String name, String date, String location, Date deadline) {
        this.name = name;
        this.date = date;
        this.location = location;
        this.deadline = deadline;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public JobFairData() {
    }

    public Long getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }
      
}
