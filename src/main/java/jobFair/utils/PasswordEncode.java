/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.utils;

import java.math.BigInteger;
import java.security.SecureRandom;
import jobFair.model.Users;
import jobFair.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author justinas
 */
@Service
public class PasswordEncode {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UsersService usersService;
    
    public String encodePassword(String password) {
        if(password == null) {
            return null;
        }
        return passwordEncoder.encode(password);
    }
    
    public boolean passwordMatch(String password, String hashedPassword) {
        if(password == null) {
            return false;
        }
        return passwordEncoder.matches(password, hashedPassword);
    }
    
    public boolean passwordMatchUser(String password, Long userId) {
        if(password == null || userId == null) {
            return false;
        }
        Users user = usersService.geUserById(userId);
        if(user == null) {
            return false;
        }
        return passwordMatch(password,user.getPassword());
    }
    
    public boolean passwordMatchLoggedUser(String password) {
        if(password == null) {
            return false;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        String userPassword = usersService.getUserByUserName(user.getUsername()).getPassword();
        if(userPassword == null) {
            return false;
        }
        return passwordMatch(password,userPassword);
    }
    
    public String generatePassword(){
       SecureRandom random = new SecureRandom();
       String clearPass = new BigInteger(50, random).toString(32);
       return encodePassword(clearPass);
   }
    
}
