/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package validation;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import jobFair.model.Users;

/**
 *
 * @author justinas
 */
public class UsersValidation {
    
    private Validator validator;

    public UsersValidation() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
   

    public String validateUser(Users user) {

        Set<ConstraintViolation<Users>> violations = validator.validate(user);

        for (ConstraintViolation<Users> violation : violations) {
            return violation.getMessage();
        }     
        return null;
    }
}
