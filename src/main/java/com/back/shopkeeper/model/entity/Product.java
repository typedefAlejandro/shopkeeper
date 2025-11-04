package com.back.shopkeeper.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "\"Product\"")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private Category category;

    @Column(unique = true)
    private String name;

    @NotNull
    @Min(0)
    private Double cost;

    @NotNull
    @Min(0)
    private Double price;

    @NotNull
    @Min(0)
    @Column(name = "qty", columnDefinition = "integer default 0")
    private Integer quantity;
}
