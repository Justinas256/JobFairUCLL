/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jobFair.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;
/**
 *
 * @author GreKar
 */
public class NotNullIfAnotherFieldHasValueValidator
    implements ConstraintValidator<NotNullIfAnotherFieldHasValue, Object> {

    private String fieldName;
    private String expectedFieldValue;
    private String dependFieldName;

    @Override
    public void initialize(NotNullIfAnotherFieldHasValue annotation) {
        fieldName          = annotation.fieldName();
        expectedFieldValue = annotation.fieldValue();
        dependFieldName    = annotation.dependFieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext ctx) {

        if (value == null) {
            return true;
        }

        BeanWrapperImpl wrapper = new BeanWrapperImpl(value);
        String fieldValue = wrapper.getPropertyValue(fieldName).toString();
        String dependFieldValue = wrapper.getPropertyValue(dependFieldName).toString();

        if (expectedFieldValue.equals(fieldValue) && dependFieldValue == null) {
            ctx.disableDefaultConstraintViolation();
            ctx.buildConstraintViolationWithTemplate(ctx.getDefaultConstraintMessageTemplate())
                .addNode(dependFieldName)
                .addConstraintViolation();
                return false;
        }
        
        return true;
    }

}