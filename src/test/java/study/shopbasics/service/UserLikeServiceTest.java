package study.shopbasics.service;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.shopbasics.dto.request.ProductSaveRequest;
import study.shopbasics.dto.request.UserLikeRequest;
import study.shopbasics.dto.request.UserSaveRequest;
import study.shopbasics.dto.response.ProductSaveResponse;
import study.shopbasics.dto.response.UserSaveResponse;
import study.shopbasics.entity.UserLike;
import study.shopbasics.repository.UserLikeRepository;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserLikeServiceTest {

    private static UserSaveResponse user;
    private static ProductSaveResponse product;

    @Autowired
    EntityManager entityManager;
    @Autowired
    private UserLikeService userLikeService;
    @Autowired
    private UserLikeRepository userLikeRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ProductService productService;

    @BeforeEach
    void setUp() {
        // Create user
        user = userService.saveUser(new UserSaveRequest("test", "test", "test@email.com"));

        // Create product
        product = productService.saveProduct(new ProductSaveRequest("name", BigDecimal.valueOf(5900), "description", "image_url", 59));
    }

    @Test
    @DisplayName("user like save test success")
    void testSaveUserLikeSuccess() {
        // Given
        Long userId = user.getId();
        Long productId = product.getId();

        // When
        userLikeService.saveUserLike(new UserLikeRequest(userId, productId));

        // Then
        UserLike userLike = userLikeRepository.findByUserIdAndProductId(userId, productId).orElseThrow();
        assertAll(
                () -> assertEquals(userLike.getUser().getId(), userId),
                () -> assertEquals(userLike.getProduct().getId(), productId)
        );
    }

    @Test
    @DisplayName("user like delete test success")
    void testDeleteUserLikeSuccess() {
        // Given
        Long userId = user.getId();
        Long productId = product.getId();
        userLikeService.saveUserLike(new UserLikeRequest(userId, productId));
        entityManager.clear();

        // When
        userLikeService.deleteUserLike(new UserLikeRequest(userId, productId));

        // Then
        assertFalse(userLikeRepository.findByUserIdAndProductId(userId, productId).isPresent());
    }
}