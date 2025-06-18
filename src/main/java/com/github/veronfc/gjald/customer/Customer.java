package com.github.veronfc.gjald.customer;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "customers")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@EqualsAndHashCode
@ToString
public class Customer {
  @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
  @Setter private String name;
  @Column(nullable = true) @Setter private String email;
  @Setter private String phone;
  @Setter private String billingAddress;
  @CreationTimestamp LocalDateTime createdAt;

  public Customer (String name, String email, String phone, String billingAddress) {
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.billingAddress = billingAddress;
  }

  public Customer (String name, String phone, String billingAddress) {
    this.name = name;
    this.phone = phone;
    this.billingAddress = billingAddress;
  }
}
