package com.github.veronfc.gjald.invoice;

import java.math.BigDecimal;

import com.github.veronfc.gjald.item.Item;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
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
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "invoiceId", nullable = false)
  private Invoice invoice;

  @OneToOne
  @JoinColumn(name = "itemId")
  private Item item;

  @NotBlank
  @Positive(message = "Quantity must be a positive integer.")
  @Setter
  private int quantity;

  @NotBlank
  @DecimalMin("0.00")
  @NonNull
  @Setter
  private BigDecimal unitPrice;

  @Transient
  private BigDecimal total;
  
  public BigDecimal getTotal() {
    return BigDecimal.valueOf(this.quantity).multiply(this.unitPrice);
  }
}
