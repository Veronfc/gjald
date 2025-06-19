package com.github.veronfc.gjald.item;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

@Table(name = "items")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Item {
  @Id @GeneratedValue(strategy = GenerationType.AUTO) private Long id;
  @NotBlank @NonNull @Setter private String name;
  @Setter private String description; //optional
  @NotBlank @NonNull @Setter private BigDecimal unitPrice;
  @NotBlank @NonNull @Setter private String unitType; // ex. hours, units, boxes, etc.
  @NotBlank @NonNull @Setter @Enumerated(EnumType.STRING) private Type type;
  @CreationTimestamp LocalDateTime createdAt;
}
