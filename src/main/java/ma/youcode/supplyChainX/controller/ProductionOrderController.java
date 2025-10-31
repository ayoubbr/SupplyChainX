package ma.youcode.supplyChainX.controller;

import ma.youcode.supplyChainX.dto.ProductionOrderRequest;
import ma.youcode.supplyChainX.dto.ProductionOrderResponse;
import ma.youcode.supplyChainX.model.ProductionOrder;
import ma.youcode.supplyChainX.service.ProductionOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/production-orders")
public class ProductionOrderController {

    private final ProductionOrderService productionOrderService;

    public ProductionOrderController(ProductionOrderService productionOrderService) {
        this.productionOrderService = productionOrderService;
    }

    @PostMapping
    public ProductionOrderResponse create(@RequestBody ProductionOrderRequest request ) {
        return productionOrderService.save(request);
    }
}
