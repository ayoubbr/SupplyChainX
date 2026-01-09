package ma.youcode.supplyChainX.mapper;

import jakarta.transaction.Transactional;
import ma.youcode.supplyChainX.dto.OrderResponse;
import ma.youcode.supplyChainX.dto.ProductRequest;
import ma.youcode.supplyChainX.dto.RawMaterialRequest;
import ma.youcode.supplyChainX.dto.RawMaterialResponse;
import ma.youcode.supplyChainX.model.Order;
import ma.youcode.supplyChainX.model.Product;
import ma.youcode.supplyChainX.model.RawMaterial;
import ma.youcode.supplyChainX.model.Supplier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional

public class RawMaterialMapper {

    public RawMaterialResponse toResponse(RawMaterial rawMaterial) {
        RawMaterialResponse rawMaterialResponse = new RawMaterialResponse();

        rawMaterialResponse.setId(rawMaterial.getId());
        rawMaterialResponse.setName(rawMaterial.getName());
        rawMaterialResponse.setUnit(rawMaterial.getUnit());
        rawMaterialResponse.setStock(rawMaterial.getStock());
        rawMaterialResponse.setMinStock(rawMaterial.getStockMin());

        return rawMaterialResponse;
    }


    public RawMaterial toEntity(RawMaterialRequest request) {
        RawMaterial rawMaterial = new RawMaterial();
        rawMaterial.setName(request.getName());
        rawMaterial.setUnit(request.getUnit());
        rawMaterial.setStock(request.getStock());
        rawMaterial.setStockMin(request.getMinStock());

        return rawMaterial;
    }
}
