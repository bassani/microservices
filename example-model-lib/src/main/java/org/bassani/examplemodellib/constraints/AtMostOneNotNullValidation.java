package org.bassani.examplemodellib.constraints;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Collection;

public class AtMostOneNotNullValidation implements ConstraintValidator<AtMostOneNotNull, Object> {

    private String[] fieldNames;

    @Override
    public void initialize(AtMostOneNotNull constraintAnnotation) {
        fieldNames = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(Object topLevelObject, ConstraintValidatorContext context) {
        BeanWrapper topLevelObjWrapper = PropertyAccessorFactory.forBeanPropertyAccess(topLevelObject);

        int fieldsNotEmpty = 0;
        for (String fieldName : fieldNames) {
            Object field = topLevelObjWrapper.getPropertyValue(fieldName);
            if (isFieldSet(field))
                fieldsNotEmpty++;
        }

        if (fieldsNotEmpty <= 1)
            return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.format("Apenas um %s pode ser definido",
                Arrays.toString(fieldNames)))
                .addConstraintViolation();
        return false;
    }

    private boolean isFieldSet(Object field) {
        return (field instanceof Collection && !((Collection<?>) field).isEmpty()) || field != null;
    }
}
