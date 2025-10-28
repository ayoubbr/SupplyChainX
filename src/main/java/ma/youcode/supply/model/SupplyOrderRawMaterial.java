package ma.youcode.supply.model;


import jakarta.persistence.*;

@Entity
@Table(name = "supply_order_raw_materials")
public class SupplyOrderRawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private RawMaterial rawMaterial;

    @ManyToOne
    private SupplyOrder supplyOrder;

    private int quantity;

    // Getters & Setters
}

