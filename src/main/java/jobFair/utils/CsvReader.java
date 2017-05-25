/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.utils;

//import com.univocity.parsers.csv.CsvParserSettings;

import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jobFair.model.Users;
import jobFair.service.UsersService;

import javax.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import validation.UsersValidation;


/**
 *
 * @author justinas
 */


public class CsvReader {
    
    @Autowired
    private PasswordEncode passwordEncode;
    
    private EmailSender emailSender = new EmailSender();
    private CsvParserSettings settings;
    private CsvParser parser;
    
    public CsvReader() {
        settings = new CsvParserSettings();
        settings.getFormat().setDelimiter(';');
        settings.getFormat().setLineSeparator("\n");
        settings.selectIndexes(0,1,2);
        parser = new CsvParser(settings);
    }
    
    public void readUsers(List<String> errors, InputStream in, UsersService service) throws MessagingException, Exception{
            List<Users> users = new ArrayList<>();
            Map<Users, String> mailList = new HashMap<>();
            UsersValidation userValidation = new UsersValidation();

            List<String[]> allRows = parser.parseAll(in, "ISO-8859-1");
            System.out.println(allRows.size());
            for (int i = 1; i < allRows.size(); i++) {
                String[] data = allRows.get(i);
                if(!(data.length < 3)) { 
                    Users user = new Users();
                    String companyName = data[0];
                    String contactName = data[1];
                    String email = data[2];

                    try {
                    user.setCompanyName(companyName);
                    user.setContactName(contactName);
                    user.setEmail(email);
                    user.generateUserId(companyName);
                    user.setRole("COMPANY");
                    String tempPass = passwordEncode.generatePassword();

                    String error = userValidation.validateUser(user);
                    if( error != null) {
                        errors.add(user.getCompanyName() + " kon niet worden toegevoegd door: " + error);
                        break;
                    } else {
                        users.add(user);
                        mailList.put(user, tempPass);
                    }   
                    } catch(Exception e) {
                        throw new Exception(e.getMessage());
                    }
            }
            if( errors == null || errors.isEmpty()) {
                service.addUsers(users);
            }

            
            try {
                emailSender.sendNewCompanyMail(mailList);
            } catch (MessagingException e) {
                throw new MessagingException(e.getMessage(), e);
            }
            
        }
    }   

}
