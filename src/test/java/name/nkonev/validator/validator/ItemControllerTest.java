package name.nkonev.validator.validator;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class ItemControllerTest extends AbstractTest{

	@Test
	void testValidationGreaterThan() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/item").param("id", "-2"))
				.andExpect(jsonPath("$.message").value("Validation error"))
				.andExpect(jsonPath("$.errors.length()").value(1))
				.andExpect(jsonPath("$.errors[0].field").value("touchItem.id"))
				.andExpect(jsonPath("$.errors[0].message").value("must be greater than or equal to 0"))
		;
	}


}
