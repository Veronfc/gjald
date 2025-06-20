package com.github.veronfc.gjald.invoice;

import org.springframework.data.jpa.repository.JpaRepository;

interface LineItemRepository extends JpaRepository<LineItem, Long> {
  
}
