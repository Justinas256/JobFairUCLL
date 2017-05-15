/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import jobFair.dao.JobFairDataRepository;
import jobFair.model.JobFairData;
import org.springframework.stereotype.Service;

/**
 *
 * @author justinas
 */
@Service
@Transactional
public class JobFairDataService {
    
    private JobFairDataRepository repository;

    public JobFairDataService(JobFairDataRepository repository) {
        this.repository = repository;
    }
    
    public List<JobFairData> findAll() {
        List<JobFairData> jobFairs = new ArrayList<>();
        for(JobFairData jobFair: repository.findAll()) {
            jobFairs.add(jobFair);
	}
        return jobFairs; 
    }
    
    public JobFairData getJobFair() {
         List<JobFairData> jobFairs = this.findAll();
         if(jobFairs.size() > 0) {
             return jobFairs.get(0);
         } 
         return null;
    }
    
    public void save(JobFairData jobFairData) {
        repository.save(jobFairData);
    } 
    
    public JobFairData geJobFairDataById(long id) {
        return repository.findOne(id);
    }
    
    public void deleteJobFairDataById(long jobFairDataId) {
	repository.delete(jobFairDataId);
    }
    
    public void delete(JobFairData jobFairData) {
	repository.delete(jobFairData);
    }
    
    public void updateDeadline(Date date){
        JobFairData jobFair = this.getJobFair();
        jobFair.setDeadline(date);
        this.save(jobFair);
    }
    
    public Date getJobFairDeadlineDate() {
	return this.getJobFair().getDeadline();
    }
    
}
