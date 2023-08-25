package study.shopbasics.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import study.shopbasics.dto.request.ProductSaveRequest;
import study.shopbasics.dto.request.ProductSearchRequest;
import study.shopbasics.dto.response.ProductFindDetailResponse;
import study.shopbasics.dto.response.ProductPageResponse;
import study.shopbasics.dto.response.ProductResponse;
import study.shopbasics.dto.response.ProductSaveResponse;
import study.shopbasics.entity.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    private static final String NAME = "name test";
    private static final BigDecimal VALID_PRICE = BigDecimal.valueOf(1000);
    private static final BigDecimal INVALID_PRICE = BigDecimal.valueOf(-1000);
    private static final String DESCRIPTION = "descriptions";
    private static final String IMAGE_URL = "/test-image-url";
    private static final Integer STOCK_QUANTITY = 100;
    private static final Integer INVALID_STOCK_QUANTITY = -100;
    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("product save test success")
    void testSaveProductSuccess() {
        // Given
        ProductSaveRequest productSaveRequest = createProductSaveRequest(VALID_PRICE, STOCK_QUANTITY);

        // When
        ProductSaveResponse productSaveResponse = productService.saveProduct(productSaveRequest);

        // Then
        assertAll(() -> assertEquals(productSaveResponse.getName(), NAME), () -> assertEquals(productSaveResponse.getDescription(), DESCRIPTION), () -> assertEquals(productSaveResponse.getPrice(), VALID_PRICE), () -> assertEquals(productSaveResponse.getImageUrl(), IMAGE_URL), () -> assertEquals(productSaveResponse.getStock(), STOCK_QUANTITY));
    }

    @Test
    @DisplayName("product save test invalid price")
    void testSaveProductFailInvalidPrice() {
        // Given
        ProductSaveRequest productSaveRequest = createProductSaveRequest(INVALID_PRICE, STOCK_QUANTITY);

        // When, Then
        assertThrows(ConstraintViolationException.class, () -> productService.saveProduct(productSaveRequest));
    }

    @Test
    @DisplayName("product save test invalid stock")
    void testSaveProductFailInvalidStock() {
        // Given
        ProductSaveRequest productSaveRequest = createProductSaveRequest(VALID_PRICE, INVALID_STOCK_QUANTITY);

        // When, Then
        assertThrows(ConstraintViolationException.class, () -> productService.saveProduct(productSaveRequest));
    }

    @Test
    @DisplayName("product save duplicate name and description")
    void testSaveProductFailDuplicateNameAndDescription() {
        // Given
        ProductSaveRequest productSaveRequest = createProductSaveRequest(VALID_PRICE, STOCK_QUANTITY);
        productService.saveProduct(productSaveRequest);

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> productService.saveProduct(productSaveRequest));
    }

    @Test
    @DisplayName("product find by search keyword test")
    void testFindProductBySearchKeyword() {
        // Given
        ProductSaveRequest productSaveRequest = createProductSaveRequest(VALID_PRICE, STOCK_QUANTITY);
        Pageable pageable = PageRequest.of(0, 20);
        productService.saveProduct(productSaveRequest);

        // When
        ProductSearchRequest productSearchRequest = new ProductSearchRequest(NAME);
        ProductPageResponse productBySearchConditions = productService.findProductBySearchConditions(productSearchRequest, pageable);

        // Then
        ProductResponse productResponse = productBySearchConditions.getItems().get(0);
        assertAll(
                () -> assertEquals(productResponse.getName(), NAME)
                , () -> assertEquals(productResponse.getDescription(), DESCRIPTION)
                , () -> assertEquals(productResponse.getPrice(), VALID_PRICE)
                , () -> assertEquals(productResponse.getImageUrl(), IMAGE_URL)
                , () -> assertEquals(productResponse.getStock(), STOCK_QUANTITY));
    }

    @Test
    @DisplayName("product find by not exist search keyword test")
    void testFindProductBySearchKeywordFail() {
        // Given
        ProductSaveRequest productSaveRequest = createProductSaveRequest(VALID_PRICE, STOCK_QUANTITY);
        Pageable pageable = PageRequest.of(0, 20);
        productService.saveProduct(productSaveRequest);

        // When
        ProductSearchRequest productSearchRequest = new ProductSearchRequest("not exist name");
        ProductPageResponse productBySearchConditions = productService.findProductBySearchConditions(productSearchRequest, pageable);

        // Then
        assertAll(
                () -> assertEquals(productBySearchConditions.getItems().size(), 0)
        );
    }

    @Test
    @DisplayName("product find by id test")
    void testFindProductById() {
        // Given
        ProductSaveRequest productSaveRequest = createProductSaveRequest(VALID_PRICE, STOCK_QUANTITY);
        ProductSaveResponse productSaveResponse = productService.saveProduct(productSaveRequest);

        // When
        ProductFindDetailResponse productResponse = productService.findProductById(productSaveResponse.getId());

        // Then
        assertAll(
                () -> assertEquals(productResponse.getName(), NAME)
                , () -> assertEquals(productResponse.getDescription(), DESCRIPTION)
                , () -> assertEquals(productResponse.getPrice(), VALID_PRICE)
                , () -> assertEquals(productResponse.getImageUrl(), IMAGE_URL)
                , () -> assertEquals(productResponse.getStock(), STOCK_QUANTITY)
                , () -> assertEquals(productResponse.getId(), productSaveResponse.getId()));
    }

    @Test
    @DisplayName("product reduce stock test")
    void testReduceProductStock() {
        // Given
        final int REDUCE_STOCK = 10;
        ProductSaveRequest productSaveRequest = createProductSaveRequest(VALID_PRICE, STOCK_QUANTITY);
        ProductSaveResponse productSaveResponse = productService.saveProduct(productSaveRequest);

        // When
        Product product = productService.reduceProductStock(productSaveResponse.getId(), REDUCE_STOCK);

        // Then
        assertAll(
                () -> assertEquals(product.getStock(), Integer.valueOf(STOCK_QUANTITY - REDUCE_STOCK))
        );
    }

    @Test
    @DisplayName("product reduce stock test invalid stock")
    void testReduceProductStockFailInvalidStock() {
        // Given
        final int REDUCE_STOCK = 1000;
        ProductSaveRequest productSaveRequest = createProductSaveRequest(VALID_PRICE, STOCK_QUANTITY);
        ProductSaveResponse productSaveResponse = productService.saveProduct(productSaveRequest);

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> productService.reduceProductStock(productSaveResponse.getId(), REDUCE_STOCK));
    }

    private ProductSaveRequest createProductSaveRequest(BigDecimal price, Integer stock) {
        return new ProductSaveRequest(NAME, price, DESCRIPTION, IMAGE_URL, stock);
    }
}
