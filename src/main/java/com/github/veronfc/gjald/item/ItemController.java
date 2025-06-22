package com.github.veronfc.gjald.item;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ServerErrorException;
import org.springframework.web.servlet.ModelAndView;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/item")
class ItemController {
  private ItemRepository db;

  ItemController(ItemRepository db) {
    this.db = db;
  }

  @GetMapping("/add")
  public ModelAndView addItemView() {
    return new ModelAndView("item/newItem", "item", new Item());
  }

  @PostMapping("/add")
  public String addItem(@ModelAttribute @Valid Item item, BindingResult result) {
    if (result.hasErrors()) {
      return "item/newItem";
    }

    try {
      Item addedItem = db.save(item);
      return String.format("redirect:/item/%s", addedItem.getId());
    } catch(Exception ex) {
      if (ex instanceof DataIntegrityViolationException divEx) {
        if (divEx.getRootCause() instanceof PSQLException psqlEx && psqlEx.getSQLState().equals("23505")) {
          String exMessage = psqlEx.getMessage();

          if (exMessage.contains("items_name_key")) {
            result.rejectValue("name", "duplicate", "Name already exists.");
          }
        }

        return "item/newItem";
      }

      throw new ServerErrorException(ex.getMessage(), ex);
    }
  }
  
  @GetMapping("/all")
  public ModelAndView getAllItems() {
    return new ModelAndView("item/allItems", "items", db.findAll());
  }
  
  @GetMapping("/{id}")
  public ModelAndView getItem(@PathVariable Long id) {
    Item item = db.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Item with ID %s does not exist.", id)));

    return new ModelAndView("item/item", "item", item);
  }
  
  @GetMapping("/{id}/update")
  public ModelAndView updateItemView(@PathVariable Long id) {
    Item item = db.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Item with ID %s does not exist.", id)));

    return new ModelAndView("item/updateItem", "item", item);
  }

  @PostMapping("/{id}/update")
  public String updateItem(@PathVariable Long id, @ModelAttribute @Valid Item item, BindingResult result) {
    if (result.hasErrors()) {
      return "item/updateItem";
    }

    try {
      Item updatedItem = db.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Item with ID %s does not exist.", id)));

      updatedItem.setName(item.getName());
      updatedItem.setDescription(item.getDescription());
      updatedItem.setType(item.getType());
      updatedItem.setUnitType(item.getUnitType());
      updatedItem.setUnitPrice(item.getUnitPrice());

      db.save(updatedItem);
      return String.format("redirect:/item/%s", updatedItem.getId());
    } catch(Exception ex) {
      if (ex instanceof DataIntegrityViolationException divEx) {
        if (divEx.getRootCause() instanceof PSQLException psqlEx && psqlEx.getSQLState().equals("23505")) {
          String exMessage = psqlEx.getMessage();

          if (exMessage.contains("items_name_key")) {
            result.rejectValue("name", "duplicate", "Name already exists.");
          }
        }

        return String.format("redirect:/item/%s/update", item.getId());
      }

      throw new ServerErrorException(ex.getMessage(), ex);
    }
  }

  @DeleteMapping("/{id}/delete")
  public String deleteItem(@PathVariable Long id) {
    db.deleteById(id);

    return "redirect:/item/all";
  }
}
