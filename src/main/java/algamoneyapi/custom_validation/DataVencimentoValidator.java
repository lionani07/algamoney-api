package algamoneyapi.custom_validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class DataVencimentoValidator implements ConstraintValidator<DataVencimentoConstraint, String> {

    @Override
    public void initialize(DataVencimentoConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(String dateVencimentoAsString, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return LocalDate.now().isBefore(LocalDate.parse(dateVencimentoAsString));
        } catch (Exception e) {
            return false;
        }

    }
}
