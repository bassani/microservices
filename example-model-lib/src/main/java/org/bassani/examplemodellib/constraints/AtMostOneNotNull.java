package org.bassani.examplemodellib.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {AtMostOneNotNullValidation.class})
public @interface AtMostOneNotNull {

    String message() default "No máximo um dos campos pode ser definido.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String[] value();
}
