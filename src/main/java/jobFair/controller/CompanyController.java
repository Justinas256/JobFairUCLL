/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import jobFair.model.Users;
import jobFair.service.UsersService;
import jobFair.utils.PasswordEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author GreKar
 */
@Controller
public class CompanyController {
    
    @Autowired
    UsersService usersService;
    
    @Autowired
    PasswordEncode passwordEncode;
    
    @GetMapping("/account")
    public ModelAndView getAccount(){  
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());
        ModelAndView model = new ModelAndView("account");
        model.addObject("contactname", user.getContactName());
        model.addObject("email", user.getEmail());
	return model;		
    }
    
    @PostMapping("/account")
    public String updateAccount(@RequestParam("contactname") String contactname, @RequestParam("email") String email, @RequestParam("password") String password, RedirectAttributes redirectAttributes){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());
        
        if (password == null || password.isEmpty()) {
            redirectAttributes.addFlashAttribute("errors", "Je bent verplicht om je wachtwoord in te geven vooraleer de wijzigingen doorgevoerd kunnen worden.");
            return "redirect:/account";
        }

        if (!passwordEncode.passwordMatchLoggedUser(password)) {
            redirectAttributes.addFlashAttribute("errors", "Verkeerd wachtwoord.");
            return "redirect:/account";
        }

        if(contactname == null || email == null || contactname.isEmpty() || email.isEmpty()){
            redirectAttributes.addFlashAttribute("errors", "Gelieve gegevens op te geven");
            return "redirect:/account";
        }
        
        usersService.save(user);
        redirectAttributes.addFlashAttribute("success", "Uw gegevens werden aangepast.");
        
        return "redirect:/account";
    }
    
    @PostMapping("/updatepassword")    
    public String updatePassword(@RequestParam("currpass") String currpass, @RequestParam("newpass") String newpas, @RequestParam("reppass") String reppas, RedirectAttributes redirectAttributes){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());
        
        if(currpass.isEmpty() || newpas.isEmpty() || reppas.isEmpty()){
            redirectAttributes.addFlashAttribute("errors", "Je bent verplicht om je wachtwoord in te geven.");
            return "redirect:/account";
        }

        if(!passwordEncode.passwordMatchLoggedUser(currpass)){
            redirectAttributes.addFlashAttribute("errors", "Huidige wachtwoord is niet correct.");
            return "redirect:/account";
        }
        
        if(!newpas.equals(reppas)){
            redirectAttributes.addFlashAttribute("errors", "Nieuw wachtwoord en herhaalde wachtwoord komen niet overeen.");
            return "redirect:/account";
        }
	user.setPassword(passwordEncode.encodePassword(newpas));
        usersService.save(user);
	redirectAttributes.addFlashAttribute("success", "Uw wachtwoord werd aangepast.");
        
        return "redirect:/account";
    }
}
