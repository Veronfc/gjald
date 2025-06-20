package com.github.veronfc.gjald.customer;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.github.veronfc.gjald.invoice.Invoice;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "customers")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Customer {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  @Setter
  private Long id;

  @Column(unique = true)
  @NotBlank
  @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long (inclusive).")
  @NonNull
  @Setter
  private String name;

  @Column(unique = true)
  @Email(message = "Email address is not well-formed.")
  @Setter
  private String email;

  @Column(unique = true)
  @NotNull
  @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits long.")
  @Setter
  private String phone;

  @NotBlank
  @Size(min = 1, max = 255, message = "Billing address must be between 1 and 255 character long.")
  @NonNull
  @Setter
  private String billingAddress;

  @CreationTimestamp
  LocalDateTime createdAt;

  @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = false)
  private List<Invoice> invoices;
}
