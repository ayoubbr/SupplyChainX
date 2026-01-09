package ma.youcode.supplyChainX.dto;

import lombok.Data;

@Data
public class SupplierRequest {
    private String name;
    private String contact;
    private double rating;
    private int leadTime;
}
