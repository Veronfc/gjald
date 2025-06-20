package com.github.veronfc.gjald.item;

import org.springframework.stereotype.Controller;

@Controller
class ItemController {
  private ItemRepository db;

  ItemController(ItemRepository db) {
    this.db = db;
  }
}
