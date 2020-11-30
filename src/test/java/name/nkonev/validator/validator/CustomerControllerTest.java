package name.nkonev.validator.validator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class CustomerControllerTest extends AbstractTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Test
	void testValidationNotNull() throws Exception {
		CustomerCreateDto createDto = new CustomerCreateDto();
		mockMvc.perform(
				MockMvcRequestBuilders.post("/customer")
						.content(objectMapper.writeValueAsBytes(createDto))
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(jsonPath("$.message").value("Validation error"))
				.andExpect(jsonPath("$.errors.length()").value(3))
		;
	}

	@Test
	void testValidationUnique() throws Exception {
		Customer customer = new Customer();
		customer.setName("John Doe");
		customer.setEmail("jd@example.com");
		customerRepository.save(customer);

		CustomerCreateDto createDto = new CustomerCreateDto();
		createDto.setName("Jim Doe");
		createDto.setEmail("jd@example.com");
		mockMvc.perform(
				MockMvcRequestBuilders.post("/customer")
						.content(objectMapper.writeValueAsBytes(createDto))
						.contentType(MediaType.APPLICATION_JSON)
		)
				.andExpect(jsonPath("$.message").value("Validation error"))
				.andExpect(jsonPath("$.errors.length()").value(1))
				.andExpect(jsonPath("$.errors[0].field").value("email"))
				.andExpect(jsonPath("$.errors[0].message").value("There is already user with this email!"))
				;
	}


}
