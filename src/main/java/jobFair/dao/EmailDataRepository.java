/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.dao;

import jobFair.model.EmailData;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author GreKar
 */
public interface EmailDataRepository extends CrudRepository<EmailData, Long>{
    
}
