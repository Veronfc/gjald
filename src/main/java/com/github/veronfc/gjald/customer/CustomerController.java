package com.github.veronfc.gjald.customer;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/customer")
class CustomerController {
  private CustomerRepository db;

  CustomerController(CustomerRepository db) {
    this.db = db;
  }
  
  @GetMapping
  public String viewCustomer(@RequestParam(required = true) Long id, Model model) {
    Customer customer = db.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Customer with ID %s does not exist", id)));
    
    model.addAttribute("customer", customer);
    return "customer/viewCustomer";
  }
  
  @PostMapping
  public String addCustomer(@ModelAttribute Customer customer, Model model) {
    //Check for conflicts with name, phone and email
    Customer newCustomer = db.save(customer);
    
    model.addAttribute("customer", newCustomer);
    return "customer/viewCustomer";
  }

  @GetMapping("/all")
  public String allCustomers(Model model) {
    model.addAttribute("customers", db.findAll());
    return "customer/allCustomers";
  }

  @GetMapping("/add")
  public String addCustomerView(Model model) {
    model.addAttribute("customer", new Customer());
    return "customer/addCustomer";
  }
}
