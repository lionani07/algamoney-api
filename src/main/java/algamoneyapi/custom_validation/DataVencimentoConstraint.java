package algamoneyapi.custom_validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = DataVencimentoValidator.class)
@Target({FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataVencimentoConstraint {
    String message() default "data deveria ser maior que hoje";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
