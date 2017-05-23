/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import jobFair.model.Spot;
import jobFair.model.Users;
import jobFair.service.SpotService;
import jobFair.service.UsersService;
import jobFair.utils.EmailSender;
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
public class LinkSpotController {
    
    @Autowired
    private SpotService spotService;
    
    @Autowired
    private UsersService usersService;
    
    @GetMapping("/linkSpot")
    public String linkSpotManually(Model model) {
        List<Spot> freeSpots = spotService.freeSpots();
        List<Users> usersNoSpot = usersService.usersWithoutSpot();
        model.addAttribute("freeSpots", freeSpots);
        model.addAttribute("freeUsers", usersNoSpot);      
        return "linkSpot";
    }
    
    @PostMapping("/linkSpot")
    public String linkSpotManuallySave(@RequestParam("spotID") String spotID, 
                                        @RequestParam("userID") String userID) throws ServletException {
        Users user = usersService.geUserById(Long.parseLong(userID));
        spotService.addUser(Long.parseLong(spotID), user);
        
        try {
            new EmailSender().sendUserLinkedToSpotMail(spotID, user.getCompanyName(), user.getEmail());
        } catch (MessagingException e) {
            throw new ServletException(e.getMessage(), e);
        }
        
        return "redirect:linkSpot";
    }
    
    
    
}
