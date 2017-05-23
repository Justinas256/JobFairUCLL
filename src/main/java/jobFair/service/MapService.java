/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import jobFair.dao.MapRepository;
import jobFair.model.Map;
import jobFair.model.SpotData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author justinas
 */

@Service
@Transactional
public class MapService {
 
    @Autowired
    private SpotDataService spotDataService;
    
    private final MapRepository repository;

    public MapService(MapRepository repository) {
        this.repository = repository;
    }
    
    public void save(Map map){
        repository.save(map);
    }
    
    public List<Map> findAll() {
        List<Map> maps = new ArrayList<>();
        for(Map map: repository.findAll()) {
            maps.add(map);
	}
        return maps; 
    }
    
    public Map geMapById(long id) {
        return repository.findOne(id);
    }
    
    public void deleteMapById(long id) {
	Map map = this.geMapById(id);
        for(SpotData spotData : map.getSpotsData()) {
            spotData.setMap(null);
            spotDataService.save(spotData);
            spotDataService.delete(spotData);
        }
        repository.delete(id);
    }
    
    public void addSpotData(Long mapId, SpotData spot) {
        Map map = this.geMapById(mapId);
        map.addSpot(spot);
        repository.save(map);   
    }
    
     public void deleteSpotData(Long mapId, SpotData spot) {
        Map map = this.geMapById(mapId);
        map.deleteSpot(spot.getId());
        repository.save(map);   
    }
    
}
