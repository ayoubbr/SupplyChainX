package ma.youcode.production.model;

import jakarta.persistence.*;
import ma.youcode.supply.model.RawMaterial;

@Entity
@Table(name = "bill_of_materials")
public class BillOfMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private RawMaterial rawMaterial;

    private int quantity;

    // Getters & Setters
}
