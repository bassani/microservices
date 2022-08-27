package org.bassani.examplemodellib.constraints;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;

public class AtLeastOneNotEmptyValidation implements ConstraintValidator<AtLeastOneNotEmpty, Object> {

    private String[] fieldNames;

    @Override
    public void initialize(AtLeastOneNotEmpty constraintAnnotation) {
        fieldNames = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object topLevel, ConstraintValidatorContext context) {
        BeanWrapper topLevelObjWrapper = PropertyAccessorFactory.forBeanPropertyAccess(topLevel);
        for (String fieldName : fieldNames) {
            Object field = topLevelObjWrapper.getPropertyValue(fieldName);
            if (isFieldSet(field))
                return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.format("Ao menos um %s deve ser definido",
                Arrays.toString(fieldNames)))
                .addConstraintViolation();
        return false;
    }

    private boolean isFieldSet(Object field) {
        return field instanceof Collection && !((Collection<?>) field).isEmpty();
    }
}
