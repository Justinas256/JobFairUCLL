/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import jobFair.model.RoleEnum;
import jobFair.model.Spot;
import jobFair.model.Users;
import jobFair.service.SpotService;
import jobFair.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author GreKar
 */
@Controller
public class HomeController {
    
    @Autowired
    UsersService usersService;
    
    @Autowired
    SpotService spotService;
    
    @GetMapping("/index")
    public ModelAndView viewHome(){
        ModelAndView model = new ModelAndView("index");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());

        if (user!=null) {
            Spot spot = spotService.getSpotFromUser(user);
            if (spot != null) {
                model.addObject("spotnr", spot.getSpotNo());
            }
        }
        return model;  
    }
}
