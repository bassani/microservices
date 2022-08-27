package org.bassani.examplemodellib.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ TYPE })
@Retention(RUNTIME)
@Constraint(validatedBy = {IfEqualLongRequireNonNullValidation.class})
public @interface IfEqualLongRequireNonNull {
    String message() default "Campo dependente não pode ser nulo dado que campo fonte é igual a certo valor.";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String sourceField();

    long[] equalsTo();

    String notNullField();
}
