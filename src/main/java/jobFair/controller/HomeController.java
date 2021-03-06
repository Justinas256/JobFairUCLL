/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;


import java.security.Principal;
import java.util.List;
import jobFair.model.Spot;
import jobFair.model.Users;
import jobFair.service.MapService;
import jobFair.service.SpotDataService;
import jobFair.service.SpotService;
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
    
    @Autowired
    private SpotDataService spotDataService;
    
    @Autowired
    private SpotService spotService;
    
    @Autowired
    private MapService mapService;
    
    @GetMapping("/home")
    public String viewHome(Model model, Principal user){
        if(user != null) {
            String userName = user.getName();
            Users company = usersService.getUserByUserName(userName);
            if(company != null) {
                String companyName = company.getCompanyName();
                model.addAttribute("companyName", companyName);
            }
            List<Spot> spots = usersService.getUserByUserName(userName).getSpots();
            if(!(spots.isEmpty())) {
                 model.addAttribute("mine", spots.get(0).getId());
            }

        }
        model.addAttribute("maps", mapService.findAll());
        model.addAttribute("bezet", spotService.takenSpots());
        
        
        return "index";  
    }
}
