/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import jobFair.model.RoleEnum;
import jobFair.model.Users;
import jobFair.service.UsersService;
import jobFair.utils.CsvReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            user.setRole(RoleEnum.COMPANY.toString());
            usersService.save(user);
            String succes = "Het bedrijf " + user.getCompanyName() + " is toegevoegd.";
            request.setAttribute("success", succes);
            /*try {
                new EmailSender().sendNewCompanyMail(user.getUsername(), tempPass, user.getEmail());
            } catch (MessagingException e) {
		throw new ServletException(e.getMessage(), e);
            }*/
	
            return "admin";
        }
    }
    
    @PostMapping("/saveuserscvs")
    public String saveUserCvs(Model model, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, Exception {  
        Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
	List<String> errors = new ArrayList<>();
	CsvReader reader = new CsvReader(); 
        
        try {
            reader.readUsers(errors, filePart.getInputStream(), usersService);
        } catch (Exception e) {
            errors.add(e.getMessage());
        }
        if (errors != null && !errors.isEmpty()) {
            request.setAttribute("errors", errors);
            model.addAttribute("errors", errors);
        } else {
            request.setAttribute("success", "De bedrijven werden toegevoegd.");
        }
        return "admin";
    }
    

}
