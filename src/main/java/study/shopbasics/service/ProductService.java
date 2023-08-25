package study.shopbasics.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import study.shopbasics.dto.request.ProductSaveRequest;
import study.shopbasics.dto.request.ProductSearchRequest;
import study.shopbasics.dto.response.ProductFindDetailResponse;
import study.shopbasics.dto.response.ProductPageResponse;
import study.shopbasics.dto.response.ProductSaveResponse;
import study.shopbasics.entity.Product;
import study.shopbasics.repository.ProductRepository;

import java.util.List;

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

    public ProductFindDetailResponse findProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return ProductFindDetailResponse.of(product);
    }

    public List<Product> findListById(List<Long> idList) {
        return productRepository.findByIdIn(idList);
    }
}
