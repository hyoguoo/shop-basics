package study.shopbasics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import study.shopbasics.dto.request.ProductSaveRequest;
import study.shopbasics.dto.request.ProductSearchRequest;
import study.shopbasics.dto.response.ProductFindDetailResponse;
import study.shopbasics.dto.response.ProductPageResponse;
import study.shopbasics.dto.response.ProductSaveResponse;
import study.shopbasics.service.ProductService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductSaveResponse saveProduct(@RequestBody @Valid ProductSaveRequest productSaveRequest) {
        return this.productService.saveProduct(productSaveRequest);
    }

    @GetMapping
    public ProductPageResponse findProductBySearchConditions(@ModelAttribute ProductSearchRequest productSearchRequest, Pageable pageable) {
        return this.productService.findProductBySearchConditions(productSearchRequest, pageable);
    }

    @GetMapping("/{id}")
    public ProductFindDetailResponse findProductById(@PathVariable Long id) {
        return this.productService.findProductById(id);
    }
}
