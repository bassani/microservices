package org.bassani.examplemodellib.constraints;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

import static java.util.Objects.isNull;

public class IfEqualLongRequireNonNullValidation implements ConstraintValidator<IfEqualLongRequireNonNull, Object> {

    private long[] values;
    private String sourceFieldName;
    private String notNullFieldName;

    @Override
    public void initialize(IfEqualLongRequireNonNull constraintAnnotation) {
        sourceFieldName = constraintAnnotation.sourceField();
        values = constraintAnnotation.equalsTo();
        notNullFieldName = constraintAnnotation.notNullField();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(obj);
        Object source = beanWrapper.getPropertyValue(sourceFieldName);
        Object target = beanWrapper.getPropertyValue(notNullFieldName);

        if (isNull(source)) return true;

        boolean equalsToAny = false;
        for (long value : values)
            if (source.equals(value)) {
                equalsToAny = true;
                break;
            }

        if (!equalsToAny) return true;

        if (!isNull(target)) return true;

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.format("%s contém {%s} logo {%s} não " +
                "pode ser nulo.", Arrays.toString(values), sourceFieldName, notNullFieldName))
                .addConstraintViolation();
        return false;
    }
}
