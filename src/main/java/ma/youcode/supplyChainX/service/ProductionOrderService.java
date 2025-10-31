package ma.youcode.supplyChainX.service;

import ma.youcode.supplyChainX.dto.ProductionOrderRequest;
import ma.youcode.supplyChainX.dto.ProductionOrderResponse;
import ma.youcode.supplyChainX.model.BillOfMaterial;
import ma.youcode.supplyChainX.model.Product;
import ma.youcode.supplyChainX.model.ProductionOrder;
import ma.youcode.supplyChainX.model.RawMaterial;
import ma.youcode.supplyChainX.repository.BillOfMaterialRepository;
import ma.youcode.supplyChainX.repository.ProductRepository;
import ma.youcode.supplyChainX.repository.ProductionOrderRepository;
import ma.youcode.supplyChainX.repository.RawMaterialRepository;
import ma.youcode.supplyChainX.shared.enums.ProductionOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductionOrderService {

    private final ProductionOrderRepository productionOrderRepository;
    private final BillOfMaterialRepository billOfMaterialRepository;
    private final RawMaterialRepository rawMaterialRepository;
    private final ProductRepository productRepository;

    public ProductionOrderService(ProductionOrderRepository productionOrderRepository,
                                  BillOfMaterialRepository billOfMaterialRepository,
                                  RawMaterialRepository rawMaterialRepository,
                                  ProductRepository productRepository) {
        this.productionOrderRepository = productionOrderRepository;
        this.billOfMaterialRepository = billOfMaterialRepository;
        this.rawMaterialRepository = rawMaterialRepository;
        this.productRepository = productRepository;
    }

    public ProductionOrderResponse save(ProductionOrderRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + request.getProductId()));

        if (request.getQuantity() <= 0) {
            throw new IllegalArgumentException("Production order quantity must be greater than zero");
        }
        if (request.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Production order start date cannot be in the past");
        }
        if (request.getEndDate().isBefore(request.getStartDate())) {
            throw new IllegalArgumentException("Production order end date cannot be before start date");
        }


        List<BillOfMaterial> bomList = product.getBillOfMaterials();
        if (bomList.isEmpty()) {
            throw new IllegalStateException("No Bill of Materials found for product: " + product.getName());
        }

        for (BillOfMaterial bom : bomList) {
            RawMaterial raw = bom.getRawMaterial();
            int requiredQty = bom.getQuantity() * request.getQuantity();

            if (raw.getStock() < requiredQty) {
                throw new IllegalStateException("Insufficient stock for raw material: " + raw.getName() +
                        " (required: " + requiredQty + ", available: " + raw.getStock() + ")");
            }
        }

        for (BillOfMaterial bom : bomList) {
            RawMaterial raw = bom.getRawMaterial();
            int requiredQty = bom.getQuantity() * request.getQuantity();
            raw.setStock(raw.getStock() - requiredQty);
            rawMaterialRepository.save(raw);
        }

        ProductionOrder productionOrder = new ProductionOrder();
        productionOrder.setProduct(product);
        productionOrder.setQuantity(request.getQuantity());
        productionOrder.setStatus(ProductionOrderStatus.EN_ATTENTE);
        productionOrder.setStartDate(request.getStartDate());
        productionOrder.setEndDate(request.getEndDate());

        ProductionOrder savedOrder = productionOrderRepository.save(productionOrder);
        return mapToResponseDTO(savedOrder);
    }

    private ProductionOrderResponse mapToResponseDTO(ProductionOrder order) {
        ProductionOrderResponse dto = new ProductionOrderResponse();
        dto.setId(order.getId());
        dto.setStatus(order.getStatus().name());
        dto.setQuantity(order.getQuantity());
        dto.setStartDate(order.getStartDate());
        dto.setEndDate(order.getEndDate());
        dto.setProductId(order.getProduct().getId());
        dto.setProductName(order.getProduct().getName());
        dto.setProductCost(order.getProduct().getCost());

        List<ProductionOrderResponse.BillOfMaterialResponse> bomResponses = order.getProduct()
                .getBillOfMaterials()
                .stream()
                .map(bom -> {
                    ProductionOrderResponse.BillOfMaterialResponse bomDto =
                            new ProductionOrderResponse.BillOfMaterialResponse();
                    bomDto.setRawMaterialId(bom.getRawMaterial().getId());
                    bomDto.setRawMaterialName(bom.getRawMaterial().getName());
                    bomDto.setQuantityPerUnit(bom.getQuantity());
                    bomDto.setTotalQuantityNeeded(bom.getQuantity() * order.getQuantity());
                    bomDto.setCurrentStock(bom.getRawMaterial().getStock());
                    return bomDto;
                })
                .toList();

        dto.setBillOfMaterials(bomResponses);
        return dto;
    }


}
