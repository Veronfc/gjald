package com.github.veronfc.gjald.invoice;

import java.math.BigDecimal;

import com.github.veronfc.gjald.customer.Customer;
import com.github.veronfc.gjald.item.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "line_items")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class LineItem {
  @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
  @ManyToOne(optional = false) @JoinColumn(name = "invoiceId", nullable = false) private Invoice invoice;
  //item - non-cascading
  @OneToOne @JoinColumn(name = "itemId") private Item item;
  @NotBlank @NonNull @Setter private int quantity;
  @NotBlank @NonNull @Setter private BigDecimal unitPrice;
  @NotBlank @NonNull @Setter private BigDecimal total;
}
