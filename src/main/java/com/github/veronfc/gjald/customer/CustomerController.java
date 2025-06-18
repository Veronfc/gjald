package com.github.veronfc.gjald.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/customer")
class CustomerController {
  private CustomerRepository db;

  CustomerController(CustomerRepository db) {
    this.db = db;
  }

  
  @GetMapping
  public String viewCustomer(@RequestParam(required = true) Long id, Model model) throws Exception {
    //Add global error handling
    Customer customer = db.findById(id).orElseThrow(() -> new Exception(String.format("Customer with ID %s does not exit", id)));
    
    model.addAttribute("customer", customer);
    return "customer/viewCustomer";
  }
  
  @PostMapping
  public String createCustomer(@ModelAttribute Customer customer, Model model) {
    Customer newCustomer = db.save(customer);
    
    model.addAttribute("customer", newCustomer);
    return "customer/viewCustomer";
  }

  @GetMapping("/create")
  public String createCustomer(Model model) {
    model.addAttribute("customer", new Customer());
    return "customer/createCustomer";
  }
}
