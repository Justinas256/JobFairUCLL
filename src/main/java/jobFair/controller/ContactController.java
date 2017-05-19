/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.io.IOException;
import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.validation.Valid;
import jobFair.model.EmailSender;
import jobFair.model.Users;
import jobFair.service.UsersService;
import jobFair.validation.VerifyRecaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author GreKar
 */

@Controller
public class ContactController {
    
    @Autowired
    private UsersService usersService;
    
    @GetMapping("/contact")
    public String getSpotsList() {
        return "contactmail";
    }
    
    @PostMapping("/contact")
    public String sendEmail(@RequestParam("name") String from, @RequestParam("subject") String subj, @RequestParam("message") String msg, RedirectAttributes redirectAttributes) throws ServletException, IOException {
        List<String> to = usersService.getAllAdminEmails();
        String scrum = "scrumbags.06@gmail.com";
        if(!to.contains(scrum)){
                to.add(scrum);
        }

        if(from.trim().isEmpty() || subj.trim().isEmpty() || msg.trim().isEmpty()){
            redirectAttributes.addFlashAttribute("errors", "Gelieve alle velden in te vullen");
            redirectAttributes.addFlashAttribute("from", from);
            redirectAttributes.addFlashAttribute("subj", subj);
            redirectAttributes.addFlashAttribute("msg", msg);
        }else {
            /*EmailSender es = new EmailSender();
            try{
                    es.sendQuestionMail(to, from, subj, msg);
                    redirectAttributes.addFlashAttribute("success", "Je vraag werd verzonden");
            } catch (MessagingException e){
                    throw new ServletException(e.getMessage(), e);
            }*/
        }
        return "redirect:/contact";
    }
        
}