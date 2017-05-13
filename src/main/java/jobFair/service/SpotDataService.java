/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import jobFair.dao.SpotDataRepository;
import jobFair.model.SpotData;
import org.springframework.stereotype.Service;

/**
 *
 * @author GreKar
 */

@Service
@Transactional
public class SpotDataService {
    
    private SpotDataRepository repository;

    public SpotDataService(SpotDataRepository repository) {
        this.repository = repository;
    }
    
    public List<SpotData> findAll() {
        List<SpotData> spotData = new ArrayList<>();
        for(SpotData spot: repository.findAll()) {
            spotData.add(spot);
	}
        return spotData; 
    }
    
    public void save(SpotData spotData) {
        repository.save(spotData);
    } 
    
    public SpotData geSpotDataById(long id) {
        return repository.findOne(id);
    }
    
    public void deleteSpotDataById(long spotId) {
	repository.delete(spotId);
    }
    
    public void delete(SpotData spotData) {
	repository.delete(spotData);
    }
}

