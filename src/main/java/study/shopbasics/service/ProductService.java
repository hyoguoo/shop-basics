package study.shopbasics.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import study.shopbasics.dto.request.ProductSaveRequest;
import study.shopbasics.dto.response.ProductSaveResponse;
import study.shopbasics.repository.ProductRepository;

@Service
@Validated
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductSaveResponse saveProduct(@Valid ProductSaveRequest productSaveRequest) {
        if (isDuplicateProductInfo(productSaveRequest.getName(), productSaveRequest.getDescription())) {
            throw new IllegalArgumentException("product info is already exist.");
        }
        return ProductSaveResponse.of(productRepository.save(productSaveRequest.toEntity()));
    }

    private boolean isDuplicateProductInfo(String name, String description) {
        return productRepository.findByNameAndDescription(name, description).isPresent();
    }
}
