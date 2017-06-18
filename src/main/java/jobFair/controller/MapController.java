/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.controller;

import java.io.IOException;
import jobFair.model.Map;
import jobFair.model.Spot;
import jobFair.model.SpotData;
import jobFair.service.MapService;
import jobFair.service.SpotDataService;
import jobFair.service.SpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author justinas
 */
@Controller
public class MapController {
    
    @Autowired
    private MapService mapService;
    
    @Autowired
    private SpotDataService spotDataService;
    
    @Autowired
    private SpotService spotService;
    
    @PostMapping("/uploadMap")
    public String uploadMap(@RequestParam("image") MultipartFile image, @RequestParam("name") String name,
            RedirectAttributes redirectAttributes) throws IOException {
        if(image == null || name == null || name.length() == 0 || image.isEmpty() || image.getSize() < 100) {
            redirectAttributes.addFlashAttribute("errors", "Please enter name and select image"); 
            return "redirect:/admin";
        }
        try {
            Map map = new Map(name, image.getBytes(), null);
            mapService.save(map);
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("errors", "Map was not saved " + ex.getLocalizedMessage()); 
            return "redirect:/admin";
        }
        redirectAttributes.addFlashAttribute("success", "Map " + name + " was saved successfully"); 
        return "redirect:/admin";
        
    }
    
    @GetMapping("/maps")
    public String maps(Model model, RedirectAttributes redirectAttributes) {
        if(mapService.findAll().isEmpty()){
            redirectAttributes.addFlashAttribute("errors", "Application does not have map. Please upload map."); 
            return "redirect:/admin";
        }
        model.addAttribute("maps", mapService.findAll());
        return "maps";
    }
    
    @RequestMapping(value="/map", params = "delete", method=RequestMethod.POST)
    public String deleteMap(Model model, @RequestParam("mapID") Long mapID, RedirectAttributes redirectAttributes) {
        String name;
        try{
            name = mapService.geMapById(mapID).getName();
            mapService.deleteMapById(mapID);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errors", "Map was not deleted"); 
            return "redirect:/maps";
        }
        redirectAttributes.addFlashAttribute("success", "Map " + name + " was successfully deleted"); 
        return "redirect:/maps";
    }
    
    
    
    @RequestMapping(value="/map", params = "addSpots", method=RequestMethod.POST)
    public String addSpotsMap(Model model, @RequestParam("mapID") Long mapID, RedirectAttributes redirectAttributes) {
        return "redirect:/addspotmap?mapID=" + mapID;
    }
    
    @PostMapping("/addSpotToMap")
    public String addSpotData(Model model, @RequestParam("spotID") Long spotID, @RequestParam("coords") String coords, @RequestParam("mapID") Long mapID,
            @RequestParam("spotNo") String spotNo, RedirectAttributes redirectAttributes){
        Map map = null;
        if(spotID != null && spotID > 0 && !(spotNo.isEmpty())) {
            SpotData spotData = spotDataService.geSpotDataById(spotID);
            spotData.setCoords(coords);
            spotData.getSpot().setSpotNo(spotNo);
            spotDataService.save(spotData);
            redirectAttributes.addFlashAttribute("success", "Spot successfully updated!");
        } else if(!(coords.isEmpty()) && mapID != null && !(spotNo.isEmpty())) {
            map = mapService.geMapById(mapID);
            SpotData spotData = new SpotData(coords,map);
            spotDataService.save(spotData);
            Spot spot = new Spot(spotNo, spotData);
            spotService.save(spot);
            redirectAttributes.addFlashAttribute("success", "Spot successfully saved!");
        } else {
            return "maps";
        }
        return "redirect:/addspotmap?mapID=" + mapID;
    }
    
    @GetMapping(value="/addspotmap")
    public String addSpotsMapGet(Model model, @RequestParam("mapID") Long mapID, RedirectAttributes redirectAttributes) {
        try{
            Map map = mapService.geMapById(mapID);
            model.addAttribute("map", map);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errors", "Error"); 
            return "redirect:/maps";
        }
        return "addSpotsMap";
    }
    
    
    
    
    @RequestMapping(value="/map", params = "showSpots", method=RequestMethod.POST)
    public String showSpotsMap(Model model, @RequestParam("mapID") Long mapID, RedirectAttributes redirectAttributes) {
        return "redirect:/showmap?mapID=" + mapID;
    }
    
    @GetMapping(value="/showmap")
    public String showSpotsMapGet(Model model, @RequestParam("mapID") Long mapID, RedirectAttributes redirectAttributes) {
        try{
            Map map = mapService.geMapById(mapID);
            model.addAttribute("map", map);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errors", "Error"); 
            return "redirect:/maps";
        }
        return "showSpotsMap";
    }

    
    @GetMapping(value = "/mapeditspot")
    public String editMapSpot(Model model, @RequestParam("spotID") Long spotID)  {
      SpotData spot = spotDataService.geSpotDataById(spotID);
      Map map = spot.getMap();
      model.addAttribute("map", map);
      model.addAttribute("spot", spot);
      model.addAttribute("spotNo", spot.getSpot().getSpotNo());
      return "addSpotsMap";
    }
    
    
    
    @RequestMapping(value = "/map/image/{mapID}")
    @ResponseBody
    public byte[] getMapImage(@PathVariable long mapID)  {
      Map map = mapService.geMapById(mapID);
      if(map.getImage() == null || map.getImage().length < 5)
        return null;
      return map.getImage();
    }
    
}
