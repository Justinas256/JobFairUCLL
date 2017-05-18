/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import jobFair.utils.PasswordEncode;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author justinas
 */

/*@NotNullIfAnotherFieldHasValue.List({
@NotNullIfAnotherFieldHasValue(
    fieldName = "role",
    fieldValue = "ADMIN",
    dependFieldName = "companyName")
})*/
@Entity
public class Users implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
 
    @Column
    @NotEmpty(message="Gelieve een contactpersoon op te geven.")
    private String contactName;
    
    @Column
    private String companyName;
    
    @Column
    @NotEmpty(message=("Er is geen email gegeven"))
    @Email(message="Email heeft niet het juiste formaat")
    private String email;
    
    @Column
    private String username; 
    
    @Column
    private String password;
    
    @Column
    private String salt;
    
    @Column
    private String role;
    
    @OneToMany(mappedBy = "user")
    private List<Spot> spots; 

    public Users(Long id, String contactName, String companyName, String email, String username, String password, String salt, String role, List<Spot> spots) {
        this.id = id;
        this.contactName = contactName;
        this.companyName = companyName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.role = role;
        this.spots = spots;
    }

    public Users(String contactName, String companyName, String email, String username, String password, String salt, String role, List<Spot> spots) {
        this.contactName = contactName;
        this.companyName = companyName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.role = role;
        this.spots = spots;
    }

    public void addSpot(Spot spot) {
        if(this.spots == null) {
            spots = new ArrayList<Spot>();
        }
        spots.add(spot);
    }
    
    public void deleteSpot(Long spotId) {
        for(Spot spot: spots) {
            if(spot.getId().equals(id)) {
                spots.remove(spot);
            }
        }
    }
    
    public List<Spot> getSpots() {
        return spots;
    }

    public void setSpots(List<Spot> spots) {
        this.spots = spots;
    }
    
    public Users() {
    }

    public String getContactName() {
        return contactName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getEmail() {
        return email;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getRole() {
        return role;
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
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "jobFair.model.Users[ id=" + id + " ]";
    }
    
    public void setPasswordHashed(@NotEmpty(message="Geen wachtwoord gegeven") String password) {
        try {
            if (this.getSalt() == null) {
                this.createSalt();
            }
            this.password = this.hashPassword(password);
        } catch (Exception e) {
            throw new IllegalArgumentException("Systeem kon geen hashed wachtwoord creeren");
        }
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        crypt.reset();

        crypt.update(this.getSalt().getBytes("UTF-8"));
        crypt.update(password.getBytes("UTF-8"));

        byte[] digest = crypt.digest();
        return new BigInteger(1, digest).toString(16);
    }
    
    private void createSalt() {
        SecureRandom random = new SecureRandom();
        byte seed[] = random.generateSeed(20);
        this.setSalt(new BigInteger(1, seed).toString(16));
    }
    
    public String generatePassword(){
       SecureRandom random = new SecureRandom();
       String clearPass = new BigInteger(50, random).toString(32);
       this.setPasswordHashed(clearPass);
       return clearPass;
   }

    public void generateUserId(@NotEmpty(message="Er kon geen gebruikersnaam gegenereerd worden.") String user){
       Random random = new Random();
       String userID = user + random.nextInt(999);
       this.setUsername(userID.replace(" ", ""));
   }
    
    public boolean isCorrectPassword(String password) {
        if(password.isEmpty()){
            throw new IllegalArgumentException("Geen wachtwoord gegeven");
        }
        try {
            return getPassword().equals(hashPassword(password));
        } catch (Exception e) {
            throw new IllegalArgumentException("Systeem kon geen hashed wachtwoord creeren");
        }
    }
}
