package ma.youcode.delivery.model;

import jakarta.persistence.*;
import ma.youcode.production.model.Product;
import ma.youcode.shared.enums.OrderStatus;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;

    private int quantity;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne
    private Address address;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Delivery delivery;

    // Getters & Setters
}
