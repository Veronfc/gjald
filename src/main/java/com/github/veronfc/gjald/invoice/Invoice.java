package com.github.veronfc.gjald.invoice;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.github.veronfc.gjald.customer.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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

@Table(name = "invoices")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Invoice {
  @Id @GeneratedValue(strategy = GenerationType.AUTO) Long id;
  @ManyToOne(optional = true) @JoinColumn(name = "customerId", nullable = true) private Customer customer;
  @CreationTimestamp private LocalDateTime issueDate;
  @Setter private LocalDate dueDate; //optional
  @UpdateTimestamp private LocalDateTime updatedAt;
  @NotBlank @NonNull @Setter @Enumerated(EnumType.STRING) private Status status;
  @Setter private String notes; //optional
  @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true) private List<LineItem> lineItems;
  @NotBlank @NonNull @Setter private BigDecimal subTotal;
  @NotBlank @NonNull @Setter private BigDecimal taxAmount;
  @NotBlank @NonNull @Setter private BigDecimal total;
}
