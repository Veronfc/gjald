package com.github.veronfc.gjald.customer;

import org.postgresql.util.PSQLException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
class CustomerService {
  public BindingResult checkUniqueContraints(DataIntegrityViolationException ex, BindingResult result) {
    if (ex.getRootCause() instanceof PSQLException psqlEx && psqlEx.getSQLState().equals("23505")) {
      String exMessage = psqlEx.getMessage();

      if (exMessage.contains("customers_email_key")) {
        result.rejectValue("email", "duplicate", "Email address already exists.");
      } else if (exMessage.contains("customers_phone_key")) {
        result.rejectValue("phone", "duplicate", "Phone number already exists.");
      } else if (exMessage.contains("customers_name_key")) {
        result.rejectValue("name", "duplicate", "Name already exists.");
      }
    }

    return result;
  }
}
