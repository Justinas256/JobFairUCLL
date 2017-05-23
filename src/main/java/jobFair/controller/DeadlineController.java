/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import jobFair.service.JobFairDataService;
import jobFair.utils.StringToCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author justinas
 */
@Controller
public class DeadlineController {
    
    @Autowired
    private JobFairDataService jobFairDataService;
    
    @PostMapping("/setdeadline")
    public String setDeadline(@RequestParam("date") String date, Model model, RedirectAttributes redirectAttributes) {
        List<String> errors = new ArrayList<>();
        
        StringToCalendar toCalendar = new StringToCalendar(date);
        Date deadline = toCalendar.getDateFormat(errors);
        
        if (errors.size() > 0) {
            redirectAttributes.addFlashAttribute("errors", errors);
        } else {
            jobFairDataService.updateDeadline(deadline);
            String dateStr = new SimpleDateFormat("EEEE").format(deadline) + " "
                + new SimpleDateFormat("dd").format(deadline) + " "
                + new SimpleDateFormat("MMMM").format(deadline) + " "
                + new SimpleDateFormat("yyyy").format(deadline);
            redirectAttributes.addFlashAttribute("success", "Je hebt de deadline gewijzigd naar: " + dateStr); 
        }
        return "redirect:/admin";
    }
}
