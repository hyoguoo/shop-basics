package study.shopbasics.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shopbasics.entity.OrderInfo;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveResponse {

    private Long id;
    private String orderNumber;
    private BigDecimal totalAmount;
    private LocalDateTime orderDate;

    public static OrderSaveResponse of(final OrderInfo orderInfo) {
        return new OrderSaveResponse(
                orderInfo.getId(),
                orderInfo.getOrderNumber(),
                orderInfo.getTotalAmount(),
                orderInfo.getOrderDate()
        );
    }
}
