/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;


import java.security.Principal;
import jobFair.model.Users;
import jobFair.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author GreKar
 */
@Controller
public class HomeController {
    
    @Autowired
    private UsersService usersService;
    
    @GetMapping("/home")
    public String viewHome(Model model, Principal user){
        if(user != null) {
            String userName = user.getName();
            Users company = usersService.getUserByUserName(userName);
            if(company != null) {
                String companyName = company.getCompanyName();
                model.addAttribute("companyName", companyName);
            }
            
        }
       
        return "index";  
    }
}
