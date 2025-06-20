package com.github.veronfc.gjald.customer;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
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
  private CustomerService service;

  CustomerController(CustomerRepository db, CustomerService service) {
    this.db = db;
    this.service = service;
  }

  @GetMapping()
  public ModelAndView viewCustomer(@RequestParam(required = true) Long id) {
    Customer customer = db.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with ID %s does not exist", id)));

    return new ModelAndView("customer/viewCustomer", "customer", customer);
  }

  @GetMapping("/all")
  public ModelAndView allCustomers() {
    return new ModelAndView("customer/allCustomers", "customers", db.findAll());
  }

  @GetMapping("/add")
  public ModelAndView addCustomerView() {
    return new ModelAndView("customer/addCustomer", "customer", new Customer());
  }

  @PostMapping("/add")
  public String addCustomer(@ModelAttribute @Valid Customer customer, BindingResult result) {
    if (result.hasErrors()) {
      return "customer/addCustomer";
    }

    try {
      Customer newCustomer = db.save(customer);
      return String.format("redirect:/customer?id=%s", newCustomer.getId());
    } catch (Exception ex) {
      if (ex instanceof DataIntegrityViolationException divEx) {
        service.checkUniqueContraints(divEx, result);

        return "customer/addCustomer";
      }

      throw new ServerErrorException("A server error has occurred", ex);
    }
  }

  @GetMapping("/update")
  public ModelAndView updateCustomerView(@RequestParam(required = true) Long id) {
    Customer customer = db.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with ID %s does not exist", id)));

    return new ModelAndView("customer/updateCustomer", "customer", customer);
  }

  @PostMapping("/update")
  public String updateCustomer(@ModelAttribute @Valid Customer customer, BindingResult result) {
    if (result.hasErrors()) {
      return "customer/updateCustomer";
    }

    try {
      Customer updatedCustomer = db.findById(customer.getId())
        .orElseThrow(() -> new EntityNotFoundException(String.format("Customer with ID %s does not exist", customer.getId())));

      updatedCustomer.setName(customer.getName());
      updatedCustomer.setEmail(customer.getEmail());
      updatedCustomer.setPhone(customer.getPhone());
      updatedCustomer.setBillingAddress(customer.getBillingAddress());

      db.save(updatedCustomer);

      return String.format("redirect:/customer?id=%s", updatedCustomer.getId());
    } catch (Exception ex) {
      if (ex instanceof DataIntegrityViolationException divEx) {
        service.checkUniqueContraints(divEx, result);

        return String.format("redirect:/customer/update?id=%s", customer.getId());
      }

      throw new ServerErrorException(ex.getMessage(), ex);
    }
  }
}
