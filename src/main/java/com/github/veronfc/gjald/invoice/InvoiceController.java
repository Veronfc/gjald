package com.github.veronfc.gjald.invoice;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import com.github.veronfc.gjald.customer.Customer;
import com.github.veronfc.gjald.customer.CustomerRepository;
import com.github.veronfc.gjald.item.ItemRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/invoice")
class InvoiceController {
  private InvoiceRepository db;
  private CustomerRepository customerDb;
  private ItemRepository itemDb;

  InvoiceController(InvoiceRepository db, CustomerRepository customerDb, ItemRepository itemDb) {
    this.db = db;
    this.customerDb = customerDb;
    this.itemDb = itemDb;
  }

  @GetMapping("/{id}")
  public ModelAndView getInvoice(@PathVariable Long id) {
    Invoice invoice = db.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Invoice with ID %s does not exist", id)));

    return new ModelAndView("invoice/invoice", "invoice", invoice);
  }

  @GetMapping("/all")
  public ModelAndView getAllInvoices() {
    return new ModelAndView("invoice/allInvoices", "invoices", db.findAll());
  }

  @GetMapping("/add")
  public String addInvoiceView(@RequestParam Long customerId, Model model) {
    Customer customer = customerDb.findById(customerId).orElseThrow(() -> new EntityNotFoundException(String.format("Customer with ID %s does not exist", customerId)));

    model.addAttribute("invoice", new Invoice());
    model.addAttribute("customerId", customer.getId());
    model.addAttribute("customerName", customer.getName());
    model.addAttribute("items", itemDb.findAll());

    return "invoice/newInvoice";
  }
 
  //TODO
  @PostMapping("/add")
  public String addInvoice(@RequestParam Long customerId, @ModelAttribute @Valid Invoice invoice, BindingResult result, Model model) {
    Customer customer = customerDb.findById(customerId).orElseThrow(() -> new EntityNotFoundException(String.format("Customer with ID %s does not exist", customerId)));

    if (result.hasErrors()) {
      // model.addAttribute("customerId", customer.getId());
      // model.addAttribute("customerName", customer.getName());
      // model.addAttribute("items", itemDb.findAll());
      // model.addAttribute("lineItems", new HashMap<String, Integer>());
    
      return "invoice/newInvoice";
    }

    try {
      invoice.setCustomer(customer);
      // invoice.setLineItems(lineItems.keySet().stream().map(itemId -> {
      //   LineItem lineItem = new LineItem();
      //   Item item = itemDb.findById(itemId).orElseThrow();
      //   lineItem.setInvoice(invoice);
      //   lineItem.setItem(item);
      //   lineItem.setQuantity(lineItems.get(itemId));
      //   lineItem.setUnitPrice(item.getUnitPrice());

      //   return lineItem;
      // }).toList());

      Invoice newInvoice = db.save(invoice);
      return String.format("redirect:/invoice/%s", newInvoice.getId());
    } catch (Exception ex) {
      throw new ServerErrorException("A server error has occurred", ex);
    }
  }
}
