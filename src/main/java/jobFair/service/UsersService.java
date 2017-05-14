/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import jobFair.dao.UsersRepository;
import jobFair.model.Spot;
import jobFair.model.Users;
import org.springframework.stereotype.Service;

/**
 *
 * @author GreKar
 */

@Service
@Transactional
public class UsersService {
    
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
        List<Users> users = this.findAll();
        List<Users> noSpotUsers = new ArrayList<>();
        
        for(Users user: users) {
            if(user.getSpots() == null || user.getSpots().isEmpty()) {
                noSpotUsers.add(user);
            }
        }
        return noSpotUsers;
    }
     
    public List<Users> getAdmins() {     
        List<Users> admins = this.findAll().stream().filter(u -> "ADMIN".equals(u.getRole())).collect(Collectors.toList());
        return admins;
    }
    
    public List<String> getAllAdminEmails() {
	List<String> emails = getAdmins().stream().map(Users::getEmail).collect(Collectors.toList());	
        return emails;
    }
}