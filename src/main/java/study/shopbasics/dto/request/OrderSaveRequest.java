package study.shopbasics.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shopbasics.entity.OrderInfo;
import study.shopbasics.entity.OrderProduct;
import study.shopbasics.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveRequest {

    // TODO: user id will be replaced with token
    @NotNull(message = "memberId must not be null")
    private Long userId;

    @NotNull(message = "orderProducts must not be null")
    private OrderSaveRequestProductDetail[] orderSaveRequestProductDetails;

    @NotNull(message = "total amount must not be null")
    @DecimalMin(value = "0", message = "total amount must be positive")
    private BigDecimal totalAmount;

    public OrderInfo toEntity(User user, List<OrderProduct> orderProducts) {
        OrderInfo orderInfo = OrderInfo.builder()
                .user(user)
                .orderDate(LocalDateTime.now())
                .totalAmount(totalAmount)
                .build();
        orderInfo.addOrderProducts(orderProducts);

        return orderInfo;
    }

    public List<OrderSaveRequestProductDetail> getProductDetailAsList() {
        return Arrays.asList(orderSaveRequestProductDetails);
    }
}
