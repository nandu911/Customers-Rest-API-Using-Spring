package com.springdemo.test;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServlet;

import org.apache.taglibs.standard.tag.common.xml.WhenTag;
import org.hamcrest.CoreMatchers;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.experimental.results.ResultMatchers;
//import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockBodyContent;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.RequestResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.springdemo.entity.Customer;
import com.springdemo.rest.CustomerRestController;
import com.springdemo.service.CustomerService;

import net.minidev.json.JSONArray;

import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class TestCustomerController {
	
	private MockMvc mockMvc;
	
	//@InjectMocks
	//private CustomerRestController customerController;
	@Mock
	private CustomerService customerService;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		final CustomerRestController customerController = new CustomerRestController();
		customerController.setCustomerService(customerService);
		this.mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
		//customerController.setCustomerService(customerService);
	}
	
	
	@Test
	public void testgetcustomers() throws Exception {
		List<Customer> customers = Arrays.asList(
									new Customer(1,"Narendra","Vuddanti","narendra@gmail.com"),
									new Customer(2,"Mahendra","Bahubali","babubali@gmail.com"));
		
		Mockito.when(customerService.getCustomers()).thenReturn(customers);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/customers")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        //.andExpect(MockMvcResultMatchers.jsonPath("$q", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", CoreMatchers.is(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", CoreMatchers.is("Narendra")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", CoreMatchers.is("Vuddanti")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].email", CoreMatchers.is("narendra@gmail.com")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", CoreMatchers.is(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", CoreMatchers.is("Mahendra")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName", CoreMatchers.is("Bahubali")))
        .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", CoreMatchers.is("babubali@gmail.com")));;
	}

	@Test
	public void testgetcustomersById() throws Exception {
		List<Customer> customers = Arrays.asList(
									new Customer(1,"Narendra","Vuddanti","narendra@gmail.com"),
									new Customer(2,"Mahendra","Bahubali","babubali@gmail.com"));
		
		Mockito.when(customerService.getCustomer(1)).thenReturn(customers.get(0));
		mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/1")).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
        //.andExpect(MockMvcResultMatchers.jsonPath("$q", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("Narendra")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is("Vuddanti")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is("narendra@gmail.com")));
	}
	
	@Test
	public void testgetcustomersPost() throws Exception {
		Map<String, Object> data = new HashMap<>();
		Customer customer = new Customer(1,"Narendra","Vuddanti","narendra@gmail.com");
		data.put("firstName", customer.getFirstName());
		data.put("lastName", customer.getLastName());
		data.put("email", customer.getEmail());
		System.out.println(JSONObject.toJSONString(data));
		//ObjectMapper mapper = new ObjectMapper();
		Mockito.when(customerService.saveCustomer(customer)).thenReturn(customer);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/postcustomers").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(data)))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
        //.andExpect(MockMvcResultMatchers.jsonPath("$q", hasSize(2)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", CoreMatchers.is("Narendra")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", CoreMatchers.is("Vuddanti")))
        .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is("narendra@gmail.com")));
	}
}
