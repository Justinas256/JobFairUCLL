/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import jobFair.model.EmailData;
import jobFair.model.JobFairData;
import jobFair.model.Spot;
import jobFair.model.Users;
import jobFair.service.EmailDataService;
import jobFair.service.JobFairDataService;
import jobFair.service.SpotService;
import jobFair.service.UsersService;
import jobFair.utils.PasswordEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author GreKar
 */
@Controller
public class FillDataController {
    
    @Autowired
    private PasswordEncode passwordEncode;
    
    @Autowired
    private SpotService spotService;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private JobFairDataService jobFairDataService;
    
    @Autowired
    private EmailDataService emailDataService;
    
    @GetMapping("/fill")
    public String fillDatabase() {
        this.createData();
        return "login";
    }
    
    
    public void createData() {
        spotService.save(new Spot("!", 2,3,true,"cool", null));
        spotService.save(new Spot("2", 1,1,false,"oh yeah!", null));
        
        usersService.save(new Users("Name", "Company", "jobfairtesting@gmail.com", "user", "pass", "COMPANY", null));
   
        Users user = new Users();
        user.setPassword(passwordEncode.encodePassword("admin"));
        user.setRole("ADMIN");
        user.setUsername("admin");
        user.setCompanyName("admin");
        user.setContactName("admin");
        user.setEmail("jobfairtesting@gmail.com");
        usersService.save(user);
        
        jobFairDataService.save(new JobFairData("Job fair", "2017","", null));
        
        user = new Users();
        user.setPassword(passwordEncode.encodePassword("company"));
        user.setRole("COMPANY");
        user.setUsername("company");
        user.setCompanyName("company name");
        user.setContactName("contact name");
        user.setEmail("jobfairtesting@gmail.com");
        usersService.save(user);
        
        Spot spot = new Spot();
        spot.setChairs(2);
        spot.setElectricity(true);
        spot.setRemarks("ramarks");
        spot.setSpotNo("42");
        spot.setTables(3);
        spotService.save(spot);
        
        Calendar deadline = Calendar.getInstance();
        if(jobFairDataService.getJobFairDeadlineDate() != null){
            deadline.setTime(jobFairDataService.getJobFairDeadlineDate());    
        }else{
            deadline = null;
        }  
        String message = "Beste,\n\n";
		if (deadline==null) {
			message += "Weldra";
		} else {
			Date date = deadline.getTime();
			message	+= "Vanaf " + new SimpleDateFormat("EEEE").format(date) + " "
				+ new SimpleDateFormat("dd").format(date) + " "
				+ new SimpleDateFormat("MMMM").format(date);
		}
        message += " eindigt de mogelijkheid om zelf je plaats je kiezen op onze jobbeurs. "
				+ "U heeft nog even tijd om uw keuze te maken.\n"
				+ "Moest u uw keuze niet tijdig gemaakt hebben, zal onze verantwoordelijke "
				+ "een willekeurige plaats voor u kiezen."
				+ "\nTot binnenkort.\n\n"
				+ "--\n"
				+ "Mvg,\n"
				+ "Team Scrumbags";
        String subject = "Jobbeurs 2017 - UCLL Leuven: Herinnering";
        EmailData endmail = new EmailData(subject, message, "endmail");
        emailDataService.save(endmail);
        
    }
}
