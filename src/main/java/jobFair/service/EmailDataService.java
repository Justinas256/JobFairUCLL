/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.service;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import jobFair.dao.EmailDataRepository;
import jobFair.model.EmailData;
import org.springframework.stereotype.Service;

/**
 *
 * @author GreKar
 */
@Service
@Transactional
public class EmailDataService {
    
    private EmailDataRepository repository;

    public EmailDataService(EmailDataRepository repository) {
        this.repository = repository;
    }
    
    public List<EmailData> findAll() {
        List<EmailData> emailData = new ArrayList<>();
        for(EmailData data: repository.findAll()) {
            emailData.add(data);
	}
        return emailData; 
    }
    
    public EmailData getEmailDataByPurpose(String purpose) {
        List<EmailData> emailData = this.findAll();
        for(EmailData data: emailData) {
            if(data.getPurpose().equals(purpose)) {
                return data;
            }
        }
        return null;
    }
    
    public void save(EmailData emailData) {
        repository.save(emailData);
    } 
    
    public EmailData geEmailDataById(long id) {
        return repository.findOne(id);
    }
    
    public void deleteEmailDataById(long id) {
	repository.delete(id);
    }
    
    public void delete(EmailData emailData) {
	repository.delete(emailData);
    }
}
