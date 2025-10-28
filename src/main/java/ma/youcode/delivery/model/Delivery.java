package ma.youcode.delivery.model;

import jakarta.persistence.*;
import ma.youcode.shared.enums.DeliveryStatus;

import java.time.LocalDate;

@Entity
@Table(name = "deliveries")
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Order order;

    private String vehicle;
    private String driver;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus status;

    private LocalDate deliveryDate;
    private double cost;

    // Getters & Setters
}

