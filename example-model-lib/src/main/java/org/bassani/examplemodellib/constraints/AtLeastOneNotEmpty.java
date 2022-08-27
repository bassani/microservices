package org.bassani.examplemodellib.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {AtLeastOneNotEmptyValidation.class})
public @interface AtLeastOneNotEmpty {

    String message() default "No mínimo um dos campos não deve ser nulo";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String[] value();
}
