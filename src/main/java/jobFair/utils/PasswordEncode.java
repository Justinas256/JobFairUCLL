/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.utils;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    public String encodePassword(String password) {
        if(password == null) {
            return null;
        }
        return passwordEncoder.encode(password);
    }
    
}
