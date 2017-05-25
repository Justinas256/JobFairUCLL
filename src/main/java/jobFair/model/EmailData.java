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
import javax.persistence.Lob;

/**
 *
 * @author GreKar
 */
@Entity
public class EmailData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(length = 4000)
    private String text;
   
    @Column
    private String purpose; 
    
    @Column
    private String subject; 
    
    private String messageHeader = "<meta charset=\"ISO-8859-1\">";

    public EmailData(Long id, String subject, String text, String purpose) {
        this.id = id;
        this.subject = subject;
        this.text = text;
        this.purpose = purpose;
    }

    public EmailData(String subject, String text, String purpose) {
        this.subject = subject;
        this.text = text;
        this.purpose = purpose;
    }

    public EmailData(){
        
    }
    
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
     public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
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
        if (!(object instanceof EmailData)) {
            return false;
        }
        EmailData other = (EmailData) object;
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
