/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.validation.Valid;
import jobFair.model.RoleEnum;
import jobFair.model.Users;
import jobFair.utils.EmailSender;
import jobFair.service.SpotService;
import jobFair.service.UsersService;
import jobFair.utils.CsvReader;
import jobFair.utils.PasswordEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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
    
    @Autowired
    private SpotService spotService;
    
    @Autowired
    private PasswordEncode passwordEncode;
    
    @GetMapping("/signupcompany")
    public ModelAndView signUpUser() {
        ModelAndView modelAndView = new ModelAndView("signupuser");
        modelAndView.addObject("users", new Users());
        return modelAndView;
    }
    
    @PostMapping("/signupcompany")
    public String saveUser(@ModelAttribute("users") @Valid Users user, BindingResult bindingResult, RedirectAttributes redirectAttributes) throws ServletException, MessagingException{  
        String tempPass;
        if (bindingResult.hasErrors()) {
            return "signupuser";
        } else {
            user.generateUserId(user.getCompanyName());
            tempPass = passwordEncode.generatePassword();
            user.setRole(RoleEnum.COMPANY.toString());
            usersService.save(user);
            String succes = "Het bedrijf " + user.getCompanyName() + " is toegevoegd.";
            redirectAttributes.addFlashAttribute("success", succes);
            try {
                new EmailSender().sendNewCompanyMail(user.getUsername(), tempPass, user.getEmail());
            } catch (MessagingException e) {
		throw new ServletException(e.getMessage(), e);
            }
	
            return "redirect:/admin";
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
    
    @GetMapping("/deletecompanies")
    public ModelAndView deleteAllCompanies() {
        return new ModelAndView("deleteallusers");
    }
    
    @RequestMapping(value="/deletecompanies", params = "cancel", method=RequestMethod.POST)
    public String dropUsersCancel() {
        return "redirect:/admin";
    }
    
    @RequestMapping(value="/deletecompanies", params = "delete", method=RequestMethod.POST)
    public String deleteCompanies(Model model, RedirectAttributes redirectAttributes, @RequestParam("password") String password) {
        if(passwordEncode.passwordMatchLoggedUser(password)) {
            try {
                usersService.deleteAllCompanies();

                redirectAttributes.addFlashAttribute("success", "All companies are deleted!");
                return "redirect:/admin";
            } catch (Exception ex) {
                redirectAttributes.addFlashAttribute("errors", ex.getMessage());
            }  
        } else {
            redirectAttributes.addFlashAttribute("errors", "Incorrect password");
        }   
        return "redirect:/deletecompanies";  
    }
    
    @GetMapping("/deleteCompany")
    public String getCompany(Model model) {
        model.addAttribute("companies", usersService.getCompaniesOrdered());
        return "deleteCompany";
    }
    
    @PostMapping("/deleteCompany")
    public String deleteCompany(Model model, RedirectAttributes redirectAttributes, @RequestParam("companyID") Long companyID, 
                                            @RequestParam("password") String password) {
        
        if(passwordEncode.passwordMatchLoggedUser(password)) {
            try {
                
                Users user = usersService.geUserById(companyID); 
                String companyName = user.getCompanyName();
                
                usersService.deleteCompany(companyID);
                
                redirectAttributes.addFlashAttribute("success", "Het bedrijf " + companyName + " is verwijderd");
                return "redirect:/admin";
            } catch (Exception ex) {
                redirectAttributes.addFlashAttribute("errors", ex.getMessage());
            }  
        } else {
            redirectAttributes.addFlashAttribute("errors", "Incorrect password");
        }   
        return "redirect:/deleteCompany";
    }
    
}
