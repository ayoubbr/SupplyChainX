package ma.youcode.supply.model;

import jakarta.persistence.*;
import ma.youcode.production.model.BillOfMaterial;

import java.util.List;

@Entity
@Table(name = "raw_materials")
public class RawMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int stock;
    private int stockMin;
    private String unit;

    @ManyToMany
    @JoinTable(
            name = "rawmaterial_supplier",
            joinColumns = @JoinColumn(name = "rawmaterial_id"),
            inverseJoinColumns = @JoinColumn(name = "supplier_id")
    )
    private List<Supplier> suppliers;

    @OneToMany(mappedBy = "rawMaterial")
    private List<BillOfMaterial> billOfMaterials;

    @OneToMany(mappedBy = "rawMaterial")
    private List<SupplyOrderRawMaterial> supplyOrderRawMaterials;

    // Getters & Setters
}
