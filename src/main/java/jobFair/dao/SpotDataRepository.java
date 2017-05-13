/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.dao;

import jobFair.model.SpotData;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author justinas
 */
public interface SpotDataRepository extends CrudRepository<SpotData, Long>{
    
}
