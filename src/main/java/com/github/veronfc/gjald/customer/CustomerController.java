package com.github.veronfc.gjald.customer;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.ModelAndView;

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
  public ModelAndView viewCustomer(@RequestParam(required = true) Long id, Model model) {
    Customer customer = db.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Customer with ID %s does not exist", id)));
    
    return new ModelAndView("customer/viewCustomer", "customer", customer);
  }
  
  @GetMapping("/all")
  public ModelAndView allCustomers(Model model) {
    return new ModelAndView("customer/allCustomers", "customers", db.findAll());
  }
  
  @GetMapping("/add")
  public ModelAndView addCustomerView(Model model) {
    return new ModelAndView("customer/addCustomer", "customer", new Customer());
  }
  
  @PostMapping("/add")
  public String addCustomer(@ModelAttribute("customer") @Valid Customer customer, BindingResult result, Model model) {
    if (result.hasErrors()) {
      return "customer/addCustomer";
    }

    try {
      Customer newCustomer = db.save(customer);
      return String.format("redirect:/customer?id=%s", newCustomer.getId());
    } catch(Exception ex) {
      if (ex instanceof DataIntegrityViolationException divEx) {
        if (divEx.getRootCause() instanceof PSQLException psqlEx && psqlEx.getSQLState().equals("23505")) {
          String exMessage = psqlEx.getMessage();

          if (exMessage.contains("customers_email_key")) {
              result.rejectValue("email", "duplicate", "Email address already exists.");
          }
          
          if (exMessage.contains("customers_phone_key")) {
              result.rejectValue("phone", "duplicate", "Phone number already exists.");
          }
          
          if (exMessage.contains("customers_name_key")) {
              result.rejectValue("name", "duplicate", "Name already exists.");
          }

          return "customer/addCustomer";
        }
      }

      throw new ServerErrorException("A server error has occurred", ex);
    }   
  }
}
