package com.springdemo.rest;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import com.springdemo.entity.Customer;
import com.springdemo.exceptionhandling.*;
import com.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api")
public class CustomerRestController {

	// inject the customer service
	@Autowired
	private CustomerService customerService;
	
	//add the mapping for GET/ customers
	@GetMapping("/customers")
	public List<Customer> getCustomers(){
		List<Customer> customers = customerService.getCustomers();
		
		if(customers.isEmpty()) {
			throw new CustomersEmptyException("No Customers found!");
		}
		return customers;
	}
	
	//add the mapping for GET/ customers by ID
	@GetMapping("/customers/{customerId}")
	public Customer getCustomers(@PathVariable Integer customerId	){
		
		Customer theCustomer = customerService.getCustomer(customerId);
	 if(theCustomer == null) {
			throw new CustomerNotFoundException("Customer id not found - "+ customerId);
		}
		
		return theCustomer;
	}
	
	//add the mapping for Post/ add new customer
	@PostMapping("/customers")
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		
		List<Customer> customers = customerService.getCustomers();
		if(theCustomer.getFirstName().isEmpty() || theCustomer.getEmail().isEmpty() || theCustomer.getLastName().isEmpty()) {
			throw new NoInputException("Please give all input fields");
		}
		else if(customers.stream().anyMatch(c -> c.getEmail().equals(theCustomer.getEmail()))) {
			throw new EmailAlreadyExistsException(theCustomer.getEmail()+ " already exists!");
		}
		
		//set id to 0 so as to always Insert instead of an Update
		theCustomer.setId(0);
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	// adding search to search a customer by first name
	@RequestMapping("/search/{customerName}")
	public List<Customer> customerlist(@PathVariable String customerName) {
			List<Customer> custList = customerService.getCustomers();
			custList.sort(Comparator.comparing(Customer::getFirstName));
			// filtering customer by first name using streams and lambda expressions.
			List<Customer> fileterdList = custList.stream().filter(c -> c.getFirstName().equalsIgnoreCase(customerName)).collect(Collectors.toList());
		
		  if(fileterdList.isEmpty()) {
				throw new SearchNotFoundException(customerName+ " does not exist!");
			} 
			return fileterdList;

	}
	
	// empty search request mapping
	@RequestMapping("/search/")
	public void searchEmpty () {
		// throwing exception if input is not given for search function
		throw new NoInputException("Please input first name of the customer to search");
	}
	
}
