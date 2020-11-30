package name.nkonev.validator.validator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
public class CustomerController {

    @PostMapping("/customer")
    public void post(@RequestBody @Valid CustomerCreateDto customerDto) {

    }
}
