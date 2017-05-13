/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import jobFair.model.Users;
import jobFair.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author GreKar
 */
@Controller
public class UsersController {
    
    @Autowired
    private UsersService usersService;
    
    @GetMapping("/signup")
    public ModelAndView signUpUser() {
        ModelAndView modelAndView = new ModelAndView("signupuser");
        modelAndView.addObject("users", new Users());
        return modelAndView;
    }
    
    @PostMapping("/saveuser")
    public String saveUser(@ModelAttribute("users") @Valid Users user, BindingResult bindingResult, HttpServletRequest request) {  
        String tempPass;
        if (bindingResult.hasErrors()) {
            return "signupuser";
        } else {
            user.generateUserId(user.getCompanyName());
            tempPass = user.generatePassword();
            usersService.save(user);
            String succes = "Het bedrijf " + user.getCompanyName() + " is toegevoegd.";
            request.setAttribute("success", succes);
            /*try {
                new EmailSender().sendNewCompanyMail(user.getUserID(), tempPass, user.getEmail());
            } catch (MessagingException e) {
		throw new ServletException(e.getMessage(), e);
            }*/
	
            return "signupuser";
        }
    }

}
