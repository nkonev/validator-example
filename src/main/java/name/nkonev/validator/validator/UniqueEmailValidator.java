package name.nkonev.validator.validator;

import javax.validation.*;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && customerRepository.findByEmail(value).isEmpty();
    }

}