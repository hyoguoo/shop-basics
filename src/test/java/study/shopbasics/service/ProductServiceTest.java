package study.shopbasics.service;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.shopbasics.dto.request.ProductSaveRequest;
import study.shopbasics.dto.response.ProductSaveResponse;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ProductServiceTest {

    private static final String NAME = "test";
    private static final BigDecimal VALID_PRICE = BigDecimal.valueOf(1000);
    private static final BigDecimal INVALID_PRICE = BigDecimal.valueOf(-1000);
    private static final String DESCRIPTION = "test";
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

    private ProductSaveRequest createProductSaveRequest(BigDecimal price, Integer stock) {
        return new ProductSaveRequest(NAME, price, DESCRIPTION, IMAGE_URL, stock);
    }
}
