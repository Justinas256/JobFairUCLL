/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import jobFair.dao.UsersRepository;
import jobFair.model.RoleEnum;
import jobFair.model.Spot;
import jobFair.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author GreKar
 */

@Service
@Transactional
public class UsersService {
    
    @Autowired
    private SpotService spotService;
    
    private final UsersRepository repository;

    public UsersService(UsersRepository repository) {
        this.repository = repository;
    }
    
    public void save(Users user){
        repository.save(user);
    }
    
    public List<Users> findAll() {
        List<Users> users = new ArrayList<>();
        for(Users user: repository.findAll()) {
            users.add(user);
	}
        return users; 
    }
    
    public List<Users> findAllCompanies() {
        List<Users> users = new ArrayList<>();
        for(Users user: repository.findAll()) {
            if(user.getRole().toLowerCase().equals("company")) {
                users.add(user);
            }  
	}
        return users; 
    }

    public Users geUserById(long id) {
        return repository.findOne(id);
    }
    
    public void deleteUserById(long userId) {
	repository.delete(userId);
    }
    
    public void delete(Users user) {
	repository.delete(user);
    }
    
    public void addSpot(Long userId, Spot spot) {
        Users user = this.geUserById(userId);
        user.addSpot(spot);
        repository.save(user);   
    }
    
     public void deleteSpot(Long userId, Spot spot) {
        Users user = this.geUserById(userId);
        user.deleteSpot(spot.getId());
        repository.save(user);   
    }
    
    public List<Users> usersWithoutSpot() {
        List<Users> users = this.findAllCompanies();
        List<Users> noSpotUsers = new ArrayList<>();
        
        for(Users user: users) {
            if(user.getSpots() == null || user.getSpots().isEmpty()) {
                noSpotUsers.add(user);
            }
        }
        return noSpotUsers;
    }
     
    public List<Users> getAdmins() {     
        List<Users> admins = this.findAll().stream().filter(u -> RoleEnum.ADMIN.toString().equals(u.getRole())).collect(Collectors.toList());
        return admins;
    }
    
    public List<Users> getCompanies() {     
        List<Users> companies = this.findAll().stream().filter(u -> RoleEnum.COMPANY.toString().equals(u.getRole())).collect(Collectors.toList());
        return companies;
    }
    
    public List<String> getAllAdminEmails() {
	List<String> emails = getAdmins().stream().map(Users::getEmail).collect(Collectors.toList());	
        return emails;
    }

    public void addUsers(List<Users> users) {
        for(Users user: users) {
            this.save(user);
        }
    }
    
    public void deleteAll()
    {
        repository.deleteAll();
    }

    public List<Users> getCompaniesOrdered() {
        List<Users> users = this.findAllCompanies();
        Collections.sort(users, (Users p1, Users p2) -> p1.getCompanyName().toLowerCase().compareTo(p2.getCompanyName().toLowerCase()));
        return users;
    }
    
    public List<Users> getCompaniesOrderedByContact() {
        List<Users> users = this.findAllCompanies();
        Collections.sort(users, (Users p1, Users p2) -> p1.getContactName().toLowerCase().compareTo(p2.getContactName().toLowerCase()));
        return users;
    }
    
     public List<Users> getCompaniesOrderedByEmail() {
        List<Users> users = this.findAllCompanies();
        Collections.sort(users, (Users p1, Users p2) -> p1.getEmail().toLowerCase().compareTo(p2.getEmail().toLowerCase()));
        return users;
    }
     
     public List<Users> getCompaniesOrderedBySpot() {
        List<Users> users = this.findAllCompanies();
        Collections.sort(users, new Comparator<Users>() {
            @Override
            public int compare(Users p1, Users p2) {
                if(p1.getSpots().isEmpty() && p2.getSpots().isEmpty()) {
                    return 0;
                } 
                if(p1.getSpots().isEmpty()) {
                    return 1;
                }
                if(p2.getSpots().isEmpty()) {
                    return -1;
                }
                return p1.getSpots().get(0).getSpotNo().compareTo(p2.getSpots().get(0).getSpotNo());
            }
        });
        return users;
    }
    
    public void deleteAdmin(Long adminId){
        this.deleteUserById(adminId);
    }
    
    public List<Users> getUsersWithoutSpot(){
        List<Users> companiesWithoutSpot = this.getCompanies().stream().filter(u -> u.getSpots().isEmpty()).collect(Collectors.toList());
        return companiesWithoutSpot;
    }
    
    public List<String> getEmailFromUsersWithoutSpot(){
        List<String> emails = this.getUsersWithoutSpot().stream().map(Users::getEmail).collect(Collectors.toList());	
        return emails;
    }
    
    public Users getUserByUserName(String username){
        return this.findAll().stream()
            .filter(e -> e.getUsername().equals(username))
            .findFirst()
            .get();
    }  
    
    public void deleteCompany(Long companyID) {
        Users user = this.geUserById(companyID); 
        this.deleteCompany(user);
    }
    
    public void deleteCompany(Users user) {   
        List<Spot> userSpots = user.getSpots();
        if(userSpots != null && userSpots.size() > 0) {
            user.setSpots(new ArrayList<Spot>());
            this.save(user);
        }
        
        for(Spot spot: userSpots) {
            spot.setUser(null);
            spotService.save(spot);
        }
        this.delete(user);
    }
    
    public void deleteAllCompanies() {
        List<Users> companies = this.findAllCompanies();
        for(Users company : companies) {
            this.deleteCompany(company);
        }
    }
    
}