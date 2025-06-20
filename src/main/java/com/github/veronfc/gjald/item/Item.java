package com.github.veronfc.gjald.item;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "items")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Item {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(unique = true)
  @NotBlank
  @Size(min = 1, max = 100, message = "Name must be between 1 and 100 characters long (inclusive).")
  @NonNull
  @Setter
  private String name;

  @Size(max = 255, message = "Description must be at most 255 characters long.")
  @Setter
  private String description; // optional

  @NotBlank
  @DecimalMin("0.00")
  @NonNull
  @Setter
  private BigDecimal unitPrice;

  @NotBlank
  @Size(min = 1, max = 20, message = "Unit type must be between 1 and 20 characters long.")
  @NonNull
  @Setter
  private String unitType; // ex. hours, units, boxes, etc.

  @NotBlank
  @NonNull
  @Setter
  @Enumerated(EnumType.STRING)
  private Type type;

  @CreationTimestamp
  LocalDateTime createdAt;
}
