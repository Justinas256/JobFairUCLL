/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import jobFair.model.Users;
import jobFair.model.EmailSender;
import jobFair.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import javax.mail.MessagingException;
import javax.servlet.ServletException;

/**
 *
 * @author GreKar
 */
@Controller
public class AdminController {
    @Autowired
    private UsersService usersService;
    
    @GetMapping("/adminform")
    public ModelAndView signUpAdmin() {
        ModelAndView modelAndView = new ModelAndView("addadmin");
        modelAndView.addObject("admin", new Users());
        return modelAndView;
    }
    
    @PostMapping("/signupadmin")
    public String saveAdmin(@ModelAttribute("admin") @Valid Users user, BindingResult bindingResult, HttpServletRequest request) throws ServletException {  
        String tempPass;
        if (bindingResult.hasErrors()) {
            request.setAttribute("errors", bindingResult);
            return "addadmin";
        } else {      
            user.setRole("ADMIN");
            tempPass = user.generatePassword();
            ///user.setCompanyName("admin"); //delete this after validation
            usersService.save(user);
            String success = "De beheerder " + user.getUsername()+ " is toegevoegd.";
            request.setAttribute("success", success);
            /*try {
                new EmailSender().sendNewAdminMail(user.getUsername(), tempPass, user.getEmail());
            } catch (MessagingException e) {
                throw new ServletException(e.getMessage(), e);
            }*/
            return "admin";
        }
        
			

    }
    
}
