/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.security.Principal;
import jobFair.model.SpotData;
import jobFair.model.Users;
import jobFair.service.SpotDataService;
import jobFair.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author justinas
 */
@Controller
public class SpotDataController {
    
    @Autowired
    private SpotDataService spotDataService;
    
    @Autowired
    private UsersService usersService;
    
    @PostMapping("/try")
    public String addSpotData(@RequestParam("coords") String coords, @RequestParam("location") String location){
        if(!(coords.isEmpty() && location.isEmpty())) {
            SpotData spotData = new SpotData(coords, location);
            spotDataService.save(spotData);
        }
        
        return "index";  
    }
    
    @GetMapping("/try")
    public String addSpotData(){
        return "try";  
    }
}
