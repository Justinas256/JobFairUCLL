/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author GreKar
 */
@Controller
public class HomeController {
    
    @GetMapping("/home")
    public String viewHome(){
        //put information 
        return "index";  
    }
}
