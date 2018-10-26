package com.springdemo.test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServlet;

import org.apache.taglibs.standard.tag.common.xml.WhenTag;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jayway.jsonpath.JsonPath;
import com.springdemo.entity.Customer;
import com.springdemo.rest.CustomerRestController;
import com.springdemo.service.CustomerService;

public class CustomerControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	private CustomerService customerService;
	
	@InjectMocks
	private CustomerRestController customerController;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
	}
	
	@Test
	public void testgetcustomers() throws Exception {
		List<Customer> customers = Arrays.asList(
									new Customer(1,"Narendra","Vuddanti","narendra@gmail.com"),
									new Customer(1,"Mahendra","Bahubali","babubali@gmail.com"));
	
		Mockito.when(customerService.getCustomers()).thenReturn(customers);
		//mockMvc.perform(get("/customers")).andExpect(HttpStatus.OK).andExpect(MediaType.APPLICATION_JSON_UTF8_VALUE)
        //.andExpect(jsonPath("$", hasSize(2)))
        //.andExpect(jsonPath("$[0].id", is(1)))
        //.andExpect(jsonPath("$[0].username", is("Daenerys Targaryen")))
        //.andExpect(jsonPath("$[1].id", is(2)))
        //.andExpect(jsonPath("$[1].username", is("John Snow")));
	}

	
}
