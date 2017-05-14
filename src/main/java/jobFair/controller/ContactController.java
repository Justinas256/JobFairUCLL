/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.util.List;
import jobFair.model.Users;
import jobFair.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author GreKar
 */
@Controller
public class ContactController {
    
    @Autowired
    private UsersService usersService;
    
    @GetMapping("/contact")
    public ModelAndView getSpotsList() {
        ModelAndView model = new ModelAndView("contact");
        List<Users> admins = usersService.getAdmins();
        model.addObject("admins", admins);
        return model;
    }
}
