package com.emp.api;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.emp.api.restcontroller.EmployeeRestController;
import com.emp.api.service.EmployeeService;

@SpringBootTest
public class EmployeeRestControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	private EmployeeRestController employeeRestController;

	@Mock
	private EmployeeService employeeService;

	@BeforeEach
	public void setup() {
		mockMvc = MockMvcBuilders.standaloneSetup(employeeRestController).build();
	}

	
	@DisplayName("Test Invalid Input format for Reply message")
    @Test
    public void testInvalidInputFormatReplyMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                    .get("/api/v1/employee/{empId}","3")
                    .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$").value("Not found!"));
    }
	
}
