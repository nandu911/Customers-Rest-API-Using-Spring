package com.springdemo.test;

import java.util.Arrays;
import java.util.List;

import org.apache.taglibs.standard.tag.common.xml.WhenTag;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


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
		
		
	}

	
}
