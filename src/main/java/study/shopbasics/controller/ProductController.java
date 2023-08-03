package study.shopbasics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.shopbasics.dto.request.ProductSaveRequest;
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
}
