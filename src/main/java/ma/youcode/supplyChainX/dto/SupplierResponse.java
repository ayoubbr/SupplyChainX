package ma.youcode.supplyChainX.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class SupplierResponse {
    private Long id;
    private String name;
    private String contact;
    private Double rating;
    private Integer leadTime;
}
