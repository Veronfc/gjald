package com.github.veronfc.gjald.customer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/customer")
class CustomerController {
  private CustomerRepository db;

  CustomerController(CustomerRepository db) {
    this.db = db;
  }
  
  @GetMapping()
  public String viewCustomer(@RequestParam(required = true) Long id, Model model) {
    Customer customer = db.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Customer with ID %s does not exist", id)));
    
    model.addAttribute("customer", customer);
    return "customer/viewCustomer";
  }
  
  @PostMapping
  public String addCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult result, Model model) {
    //Check for conflicts with name, phone and email
    if (result.hasErrors()) {
      model.addAttribute("errors", result);

      return "customer/addCustomer";
    }

    Customer newCustomer = db.save(customer);
    
    return String.format("redirect:/customer?id=%s", newCustomer.getId());
  }

  //update to return ModelAndView
  @GetMapping("/all")
  public String allCustomers(Model model) {
    model.addAttribute("customers", db.findAll());
    return "customer/allCustomers";
  }

  //update to return ModelAndView
  @GetMapping("/add")
  public String addCustomerView(Model model) {
    model.addAttribute("customer", new Customer());
    return "customer/addCustomer";
  }
}
