package study.shopbasics.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.shopbasics.dto.request.OrderSaveRequest;
import study.shopbasics.dto.request.OrderSaveRequestProductDetail;
import study.shopbasics.dto.request.ProductSaveRequest;
import study.shopbasics.dto.request.UserSaveRequest;
import study.shopbasics.dto.response.OrderSaveResponse;
import study.shopbasics.dto.response.ProductSaveResponse;
import study.shopbasics.dto.response.UserSaveResponse;
import study.shopbasics.entity.OrderInfo;
import study.shopbasics.entity.OrderProduct;
import study.shopbasics.repository.OrderInfoRepository;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTest {

    private final static int PRICE1 = 1000;
    private final static int PRICE2 = 2000;
    private final static int STOCK_QUANTITY = 59;
    private static UserSaveResponse user;
    private static ProductSaveResponse product1;
    private static ProductSaveResponse product2;

    @Autowired
    EntityManager entityManager;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @BeforeEach
    void setUp() {
        // Create user
        UserSaveRequest userSaveRequest = new UserSaveRequest("test", "test", "test@email.com");
        user = userService.saveUser(userSaveRequest);
        // Create product
        ProductSaveRequest productSaveRequest1 = new ProductSaveRequest("name1", BigDecimal.valueOf(PRICE1), "description1", "image_url1", STOCK_QUANTITY);
        ProductSaveRequest productSaveRequest2 = new ProductSaveRequest("name2", BigDecimal.valueOf(PRICE2), "description2", "image_url2", STOCK_QUANTITY);
        product1 = productService.saveProduct(productSaveRequest1);
        product2 = productService.saveProduct(productSaveRequest2);
    }

    @Test
    @DisplayName("order save test success")
    void testSaveOrder() {
        // Given
        final int QUANTITY1 = 2;
        final int QUANTITY2 = 3;
        OrderSaveRequestProductDetail[] orderSaveRequestProductDetails = new OrderSaveRequestProductDetail[]{
                new OrderSaveRequestProductDetail(product1.getId(), QUANTITY1),
                new OrderSaveRequestProductDetail(product2.getId(), QUANTITY2)
        };
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest(user.getId(), orderSaveRequestProductDetails, BigDecimal.valueOf(PRICE1 * QUANTITY1 + PRICE2 * QUANTITY2));

        // When
        OrderSaveResponse orderSaveResponse = orderService.saveOrder(orderSaveRequest);
        entityManager.clear();

        // Then
        OrderInfo orderInfo = orderInfoRepository.findById(orderSaveResponse.getId()).orElseThrow(IllegalArgumentException::new);
        List<OrderProduct> orderProducts = orderInfo.getOrderProducts();
        assertAll(
                () -> assertThat(orderInfo.getUser().getId()).isEqualTo(user.getId()),
                () -> assertThat(orderInfo.getTotalAmount()).isEqualByComparingTo(BigDecimal.valueOf(PRICE1 * QUANTITY1 + PRICE2 * QUANTITY2)),
                () -> assertThat(orderProducts).isNotNull(),
                () -> assertThat(orderProducts).hasSize(2),
                () -> assertThat(orderProducts.get(0).getProduct().getId()).isEqualTo(product1.getId()),
                () -> assertThat(orderProducts.get(0).getQuantity()).isEqualTo(QUANTITY1),
                () -> assertThat(orderProducts.get(1).getProduct().getId()).isEqualTo(product2.getId()),
                () -> assertThat(orderProducts.get(1).getQuantity()).isEqualTo(QUANTITY2)
        );
    }

    @Test
    @DisplayName("order save test over stock")
    void testSaveOrderFailInvalidQuantity() {
        // Given
        final int QUANTITY1 = STOCK_QUANTITY + 1;
        final int QUANTITY2 = 3;
        OrderSaveRequestProductDetail[] orderSaveRequestProductDetails = new OrderSaveRequestProductDetail[]{
                new OrderSaveRequestProductDetail(product1.getId(), QUANTITY1),
                new OrderSaveRequestProductDetail(product2.getId(), QUANTITY2)
        };
        OrderSaveRequest orderSaveRequest = new OrderSaveRequest(user.getId(), orderSaveRequestProductDetails, BigDecimal.valueOf(PRICE1 * QUANTITY1 + PRICE2 * QUANTITY2));

        // When, Then
        assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(orderSaveRequest));
    }
}
