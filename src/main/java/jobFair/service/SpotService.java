/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import jobFair.dao.SpotRepository;
import jobFair.model.Spot;
import jobFair.model.Users;
import org.springframework.stereotype.Service;
/**
 *
 * @author justinas
 */
@Service
@Transactional
public class SpotService {
    
    private final SpotRepository repository;

    public SpotService(SpotRepository repository) {
        this.repository = repository;
    }
    
    public void save(Spot spot){
        repository.save(spot);
    }
    
    public List<Spot> findAll() {
        List<Spot> spots = new ArrayList<>();
        for(Spot spot: repository.findAll()) {
            spots.add(spot);
	}
        return spots; 
    }
    
    public Spot geSpotById(long id) {
        return repository.findOne(id);
    }
    
    public void deleteSpotById(long spotId) {
	repository.delete(spotId);
    }
    
    public void delete(Spot spot) {
	repository.delete(spot);
    }
    
    public void addUser(Long spotId, Users user) {
        Spot spot = this.geSpotById(spotId);
        spot.setUser(user);
        repository.save(spot);
    }
    
    public List<Spot> freeSpots() {
        List<Spot> spots = this.findAll();
        List<Spot> freeSpots = new ArrayList<>();
        
        for(Spot spot: spots) {
            if(spot.getUser() == null) 
                freeSpots.add(spot);
        }
        return freeSpots;
    }
    
    public List<Spot> takenSpots() {
        List<Spot> spots = this.findAll();
        List<Spot> takenSpots = new ArrayList<>();
        
        for(Spot spot: spots) {
            if(spot.getUser() != null) 
                takenSpots.add(spot);
        }
        return takenSpots;
    }

    public List<Spot> sortTakenSpots() {
        List<Spot> spots = this.takenSpots();  
        Collections.sort(spots, (Spot p1, Spot p2) -> p1.getSpotNo().compareTo(p2.getSpotNo()));
        return spots;
    }
}
