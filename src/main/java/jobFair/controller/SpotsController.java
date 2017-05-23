/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import jobFair.model.JobFairData;
import jobFair.model.Spot;
import jobFair.model.Users;
import jobFair.service.JobFairDataService;
import jobFair.service.SpotService;
import jobFair.service.UsersService;
import jobFair.utils.PasswordEncode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jobFair.model.EmailSender;

/**
 *
 * @author justinas
 */
@Controller
public class SpotsController {
    
    @Autowired
    private PasswordEncode passwordEncode;
    
    @Autowired
    private SpotService spotService;
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private JobFairDataService jobFairDataService;
    
    @GetMapping("/spots")
    public ModelAndView getSpotsList() {
        ModelAndView model = new ModelAndView("spotoverview");
        List<Spot> spots = spotService.findAll();
        model.addObject("spots", spots);
        return model;
    }
    
    @GetMapping("/freespots")
    public String getFreeSpots(Model model) {
        List<Spot> spots = spotService.freeSpots();
        model.addAttribute("spots", spots);
        return "spotoverview";
    }
    
    @GetMapping("/takenspots")
    public String getTakenSpots(Model model) {
        List<Spot> spots = spotService.takenSpots();
        model.addAttribute("spots", spots);
        return "spotoverview";
    }
    
    @GetMapping("/sorttakenspots")
    public String getSortedTakenSpots(Model model) {
        List<Spot> spots = spotService.sortTakenSpots();
        model.addAttribute("spots", spots);
        return "spotoverview";
    }
    
    
    @GetMapping("/fill")
    public String fillDatabase() {
        this.createData();
        return "login";
    }
    
    
    public void createData() {
        spotService.save(new Spot("!", 2,3,true,"cool", null));
        spotService.save(new Spot("2", 1,1,false,"oh yeah!", null));
        
        usersService.save(new Users("Name", "Company", "email@gmail.com", "user", "pass", "", "COMPANY", null));
   
        Users user = new Users();
        user.setPassword(passwordEncode.encodePassword("admin"));
        user.setRole("ADMIN");
        user.setUsername("admin");
        user.setSalt("");
        user.setCompanyName("admin");
        user.setContactName("admin");
        user.setEmail("Email@gmail.com");
        usersService.save(user);
        
        jobFairDataService.save(new JobFairData("Job fair", "2017","", null));
        
        user = new Users();
        user.setPassword(passwordEncode.encodePassword("company"));
        user.setRole("COMPANY");
        user.setUsername("company");
        user.setSalt("");
        user.setCompanyName("company name");
        user.setContactName("contact name");
        user.setEmail("contact@gmail.com");
        usersService.save(user);
        
        Spot spot = new Spot();
        spot.setChairs(2);
        spot.setElectricity(true);
        spot.setRemarks("ramarks");
        spot.setSpotNo("42");
        spot.setTables(3);
        spotService.save(spot);
        
    }
    
    @GetMapping("/myspot")
    public ModelAndView mySpot(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());
        ModelAndView model = new ModelAndView("spot");
        model.addObject("user", user);
        
        Spot spot = spotService.getSpotFromUser(user);             
        model.addObject("spot", spot);
        if (spot != null) {
            if(spot.isElectricity() == true) {
                model.addObject("electricity", "ja");
            } else model.addObject("electricity", "nee");
        }
        return model;
    }
    
    @PostMapping("/updatespot")
    public ModelAndView updateSpot(@RequestParam("spotID") Long spotID){
        ModelAndView model = new ModelAndView("updatespot");
        Spot spot = spotService.geSpotById(spotID);
        String checked = "checked";
        switch(spot.getChairs()){
            case 0:
                model.addObject("ch0", checked);
                model.addObject("ch1", "");
                model.addObject("ch2", "");
                break;
            case 1:
                model.addObject("ch0", "");
                model.addObject("ch1", checked);
                model.addObject("ch2", "");
                break;
            case 2:
                model.addObject("ch0", "");
                model.addObject("ch1", "");
                model.addObject("ch2", checked);
                break;
            default:
                model.addObject("ch0", "");
                model.addObject("ch1", "");
                model.addObject("ch2", checked);
        }

        switch(spot.getTables()){
            case 0:
                model.addObject("tb0", checked);
                model.addObject("tb1", "");
                break;
            case 1:
                model.addObject("tb0", "");
                model.addObject("tb1", checked);
                break;
            default:
                model.addObject("tb0", checked);
                model.addObject("tb1", "");
        }

        if(spot.isElectricity()){
            model.addObject("el", checked);
        } else {
            model.addObject("el", "");
        }

        model.addObject("spot", spot);
	return model;
    }
    
    @PostMapping("/cancelspot")
    public ModelAndView cancelSpot(@RequestParam("spotID") Long spotID){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());
        Spot spot = spotService.geSpotById(spotID);
        ModelAndView model = new ModelAndView("confirmspotcancel");
        model.addObject("spot", spot);
	return model;
    }
    
    @PostMapping("/confirmspotcancel")
    public String confirmCancelSpot(@RequestParam("spotID") Long spotID, @RequestParam("submit") String action, RedirectAttributes redirectAttributes){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());
        Spot spot = spotService.geSpotById(spotID);
        if(action.equals("ja")){
            redirectAttributes.addFlashAttribute("spotnr", spot.getSpotNo());
            redirectAttributes.addFlashAttribute("annuleer", "annuleer");
            redirectAttributes.addFlashAttribute("companyName", user.getCompanyName());
            spotService.removeUserFromSpot(spot.getId());
            //new EmailSender().sendCancelationMail(spot, user.getCompanyName(), user.getEmail());
            return "redirect:/home";
        }
	return "redirect:/myspot";
    }
    
    @PostMapping("/confirmupdatespot")
    public String confirmUpdateSpot(@RequestParam("spotID") Long spotID, @RequestParam("chairs") int chairs, @RequestParam("tables") int tables, @RequestParam(value="electricity", required=false) String electricity, @RequestParam("extra") String extra, RedirectAttributes redirectAttributes){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());
        Spot spot = spotService.geSpotById(spotID);
        spot.setChairs(chairs);
        spot.setTables(tables);
        if(!electricity.isEmpty()){
            spot.setElectricity(true);
        }else{
            spot.setElectricity(false);
        }
        spot.setRemarks(extra);
        spotService.save(spot);
        redirectAttributes.addFlashAttribute("spotnr", spot.getSpotNo());
        redirectAttributes.addFlashAttribute("update", "update");
        redirectAttributes.addFlashAttribute("companyName", user.getCompanyName());

        /*try {
                new EmailSender().sendUpdateMail(spot, user.getCompanyName(), user.getEmail());
        } catch (MessagingException e) {
                throw new ServletException(e.getMessage(), e);
        }*/

        return "redirect:/home";
    }
}
