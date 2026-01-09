package ma.youcode.supplyChainX.dto;

import lombok.Data;
import ma.youcode.supplyChainX.model.Supplier;

import java.util.List;

@Data
public class RawMaterialRequest {
    private String name;
    private int stock;
    private int minStock;
    private String unit;
    private List<Long> supplierIds;
}