package com.github.veronfc.gjald.invoice;

import org.springframework.stereotype.Controller;

@Controller
class InvoiceController {
  private InvoiceRepository db;

  InvoiceController(InvoiceRepository db) {
    this.db = db;
  }
}
