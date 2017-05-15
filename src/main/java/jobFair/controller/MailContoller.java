/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.util.Calendar;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import jobFair.model.EmailSender;
import jobFair.service.JobFairDataService;
import jobFair.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author GreKar
 */
@Controller
public class MailContoller {
    @Autowired
    UsersService usersService;
    
    @Autowired 
    JobFairDataService jobFairDataService;
    
    @PostMapping("/endmail")
    public String endMail(RedirectAttributes redirectAttributes) throws ServletException, MessagingException{
        EmailSender emailSender = new EmailSender();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(jobFairDataService.getJobFairDeadlineDate());
        //emailSender.sendEndOfRegistrationMail(calendar, usersService.getEmailFromUsersWithoutSpot());  //TODO:: uncomment
        redirectAttributes.addFlashAttribute("success", "Je mails werden verstuurd");
        return "redirect:/admin";

    }
}
