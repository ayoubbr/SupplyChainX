package ma.youcode.production.model;

import jakarta.persistence.*;
import ma.youcode.shared.enums.ProductionOrderStatus;

import java.time.LocalDate;

@Entity
@Table(name = "production_orders")
public class ProductionOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private ProductionOrderStatus status;

    private LocalDate startDate;
    private LocalDate endDate;

    // Getters & Setters
}

