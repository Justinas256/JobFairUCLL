/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.util.List;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
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
import jobFair.utils.EmailSender;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String confirmCancelSpot(@RequestParam("spotID") Long spotID, @RequestParam("submit") String action, RedirectAttributes redirectAttributes) throws ServletException{
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());
        Spot spot = spotService.geSpotById(spotID);
        if(action.equals("ja")){
            redirectAttributes.addFlashAttribute("spotnr", spot.getSpotNo());
            redirectAttributes.addFlashAttribute("annuleer", "annuleer");
            redirectAttributes.addFlashAttribute("companyName", user.getCompanyName());
            spotService.removeUserFromSpot(spot.getId());
            try{
                new EmailSender().sendCancelationMail(spot, user.getCompanyName(), user.getEmail());
            } catch (MessagingException e) {
                throw new ServletException(e.getMessage(), e);
            }
            return "redirect:/home";
        }
	return "redirect:/myspot";
    }
    
    @PostMapping("/confirmupdatespot")
    public String confirmUpdateSpot(@RequestParam("spotID") Long spotID, @RequestParam("chairs") int chairs, @RequestParam("tables") int tables, @RequestParam(value="electricity", required=false) String electricity, @RequestParam("extra") String extra, RedirectAttributes redirectAttributes) throws ServletException{
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

        try {
                new EmailSender().sendUpdateMail(spot, user.getCompanyName(), user.getEmail());
        } catch (MessagingException e) {
                throw new ServletException(e.getMessage(), e);
        }

        return "redirect:/home";
    }
    
    @PostMapping("/spotoptions")
    public String setSpotOptions(@RequestParam("spotID") Long spotID, @RequestParam("chairs") int chairs, @RequestParam("tables") int tables,
            @RequestParam(value="electricity", required=false) String electricity, @RequestParam("extra") String extra, RedirectAttributes redirectAttributes)
			throws ServletException, MessagingException{
        boolean electricityBool = false;
        if(electricity != null){
                electricityBool = true;
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());

        Spot spot = spotService.geSpotById(spotID);
        spot.setUser(user);
        spot.setTables(tables);
        spot.setChairs(chairs);
        spot.setRemarks(extra);
        spot.setElectricity(electricityBool);
        spotService.save(spot);

        redirectAttributes.addFlashAttribute("spotnr", spotID);
        redirectAttributes.addFlashAttribute("reserved", "Uw plaats werd gereserveerd. U ontvangt een mail ter bevestiging.");
        
        try {
            new EmailSender().sendConfirmationMail(spot, user.getCompanyName(), user.getEmail());
            if (spotService.freeSpots().size() < 10) {
                new EmailSender().sendAlmostSoldOutMail(usersService.getAllAdminEmails());
            }
        } catch (MessagingException e) {
            throw new ServletException(e.getMessage(), e);
        }

        redirectAttributes.addFlashAttribute("reserveer", "reserveer");
        return "redirect:/home";
    }
   
    @PostMapping("/spotadminoptions")
    public String setSpotOptionsAdmin(@RequestParam("spotID") Long spotID, @RequestParam("chairs") int chairs, @RequestParam("tables") int tables,
            @RequestParam(value="electricity", required=false) String electricity, @RequestParam("extra") String extra, RedirectAttributes redirectAttributes)
            throws ServletException{
       	
	boolean electricityBool = false;
        if(electricity != null){
                electricityBool = true;
        }
        
	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());

        Spot spot = spotService.geSpotById(spotID);
        spot.setUser(user);
        spot.setTables(tables);
        spot.setChairs(chairs);
        spot.setRemarks(extra);
        spot.setElectricity(electricityBool);
        spotService.save(spot);
        
        try {
                new EmailSender().sendUserLinkedToSpotMail(spot.getSpotNo(), user.getCompanyName(), user.getEmail());
        } catch (MessagingException e) {
                throw new ServletException(e.getMessage(), e);
        }

        return "redirect:/home";
    }
    
    @GetMapping("/showopt&id={id}")
    public ModelAndView getSpotOptions(@PathVariable Long id, RedirectAttributes redirectAttributes){
        ModelAndView model = new ModelAndView();
        Spot spot = spotService.geSpotById(id);
	model.addObject("spot", spot);

	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersService.getUserByUserName(auth.getName());

        //Afhandelen van het reserveren van een tweede spot.
        if (spotService.getSpotFromUser(user) != null)
        {
            redirectAttributes.addFlashAttribute("errors", "Er werd al een plaats gereserveerd voor " + user.getCompanyName() + ".");
            model.setViewName("redirect:/home");
            return model;
        }

        //Afhandelen van het reserveren van een reeds bezette spot.
        if (spot.getUser()!= null && !spot.getUser().equals(user)) {
            redirectAttributes.addFlashAttribute("userID", user.getId());
            spot = spotService.getSpotFromUser(user);
            if (spot != null){
                redirectAttributes.addFlashAttribute("mine", spot.getSpotNo());
            }
            redirectAttributes.addFlashAttribute("bezet", spotService.takenSpots());
            redirectAttributes.addFlashAttribute("errors", "Spot "+spot.getSpotNo()+" is al gereserveerd door het bedrijf "+user.getCompanyName()+".");

            model.setViewName("redirect:/home");
            return model;
        }
		
        if (user.getRole().equals("ADMIN")) {
            redirectAttributes.addFlashAttribute("freeUser", usersService.getUsersWithoutSpot());
            model.setViewName("spotoptionsadmin");
            return model;
        } else {
            model.setViewName("spotoptions");
            return model;
        }
    }
}
