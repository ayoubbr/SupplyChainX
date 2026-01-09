package ma.youcode.supplyChainX.mapper;

import lombok.RequiredArgsConstructor;
import ma.youcode.supplyChainX.dto.SupplierRequest;
import ma.youcode.supplyChainX.dto.SupplierResponse;
import ma.youcode.supplyChainX.model.Supplier;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SupplierMapper {

    public SupplierResponse toResponse(Supplier supplier) {
        SupplierResponse supplierResponse = new SupplierResponse();

        supplierResponse.setId(supplier.getId());
        supplierResponse.setName(supplier.getName());
        supplierResponse.setId(supplier.getId());
        supplierResponse.setLeadTime(supplier.getLeadTime());
        supplierResponse.setRating(supplier.getRating());
        supplierResponse.setContact(supplier.getContact());

        return supplierResponse;
    }

    public Supplier toEntity(SupplierRequest supplierRequest) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierRequest.getName());
        supplier.setContact(supplierRequest.getContact());
        supplier.setLeadTime(supplierRequest.getLeadTime());
        supplier.setRating(supplierRequest.getRating());
        return supplier;
    }

    public Supplier toEntity(SupplierResponse supplierResponse) {
        Supplier supplier = new Supplier();
        supplier.setId(supplierResponse.getId());
        supplier.setName(supplierResponse.getName());
        supplier.setContact(supplierResponse.getContact());
        supplier.setLeadTime(supplierResponse.getLeadTime());
        supplier.setRating(supplierResponse.getRating());
        return supplier;
    }
}
