/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import jobFair.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author GreKar
 */
@Controller
public class AdminController {
    @PostMapping("/adminform")
    public ModelAndView signUpUser() {
        ModelAndView modelAndView = new ModelAndView("addadmin");
        modelAndView.addObject("admin", new Users());
        return modelAndView;
    }
    
    @PostMapping("/addadmin")
    public String saveUser(@ModelAttribute("admin") @Valid Users user, BindingResult bindingResult, HttpServletRequest request) {  
        String tempPass;
        if (bindingResult.hasErrors()) {
            return "adminform";
        } else {
            
            return "home";
        }
    }
}
