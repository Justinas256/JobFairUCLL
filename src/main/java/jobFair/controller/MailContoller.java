/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import jobFair.model.EmailData;
import jobFair.utils.EmailSender;
import jobFair.service.EmailDataService;
import jobFair.service.JobFairDataService;
import jobFair.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.ModelAndView;

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
    
    @Autowired
    private EmailDataService emailDataService;
    
    @PostMapping("/endmail")
    public String endMail(@RequestParam("endemailid") Long endemailid, @RequestParam("endmailsubject") String endmailsubject, @RequestParam("endmailtext") String endmailtext, RedirectAttributes redirectAttributes) throws ServletException, MessagingException{
        EmailData endmail = emailDataService.geEmailDataById(endemailid);
        endmail.setText(endmailtext);
        emailDataService.save(endmail);
        
        EmailSender emailSender = new EmailSender();      
        emailSender.sendEndOfRegistrationMail(null, usersService.getEmailFromUsersWithoutSpot(), endmailsubject, endmailtext);
        
        redirectAttributes.addFlashAttribute("success", "Je mails werden verstuurd");
        return "redirect:/admin";

    }
    
    @GetMapping("/endmail")
    public ModelAndView getMailText(){
        ModelAndView model = new ModelAndView("emailText");
        EmailData endmail = emailDataService.getEmailDataByPurpose("endmail");
        model.addObject("endmail", endmail);
        
        String deadlinedate = " ";
        if(jobFairDataService.getJobFairDeadlineDate() != null){
            Calendar deadline = Calendar.getInstance();
            deadline.setTime(jobFairDataService.getJobFairDeadlineDate());  
            Date date = deadline.getTime();
            deadlinedate += new SimpleDateFormat("EEEE").format(date) + " "
				+ new SimpleDateFormat("dd").format(date) + " "
				+ new SimpleDateFormat("MMMM").format(date) + " ";
        }

        model.addObject("deadlinedate", deadlinedate);
        return model;
    }
}
