package com.github.veronfc.gjald.invoice;

import org.springframework.stereotype.Controller;

@Controller
class LineItemController {
  private LineItemRepository db;

  LineItemController(LineItemRepository db) {
    this.db = db;
  }
}
