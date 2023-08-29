package study.shopbasics.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import study.shopbasics.dto.request.ProductSaveRequest;
import study.shopbasics.dto.request.ProductSearchRequest;
import study.shopbasics.dto.response.ProductFindDetailResponse;
import study.shopbasics.dto.response.ProductPageResponse;
import study.shopbasics.dto.response.ProductSaveResponse;
import study.shopbasics.entity.Product;
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


    public ProductPageResponse findProductBySearchConditions(ProductSearchRequest productSearchRequest, Pageable pageable) {
        Page<Product> pageProduct = productRepository.findWithSearchKeyword(productSearchRequest.getSearchKeyword(), pageable);
        return ProductPageResponse.of(pageProduct);
    }

    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }

    public ProductFindDetailResponse findProductById(Long id) {
        Product product = findById(id);
        return ProductFindDetailResponse.of(product);
    }

    @Transactional
    public Product reduceProductStock(Long productId, Integer reduceStock) {
        Product product = findById(productId);

        if (product.getStock() < reduceStock) {
            throw new IllegalArgumentException("product stock is not enough.");
        }

        product.reduceStock(reduceStock);

        return product;
    }
}
