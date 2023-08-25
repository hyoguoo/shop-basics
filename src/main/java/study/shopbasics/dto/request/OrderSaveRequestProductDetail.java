package study.shopbasics.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shopbasics.entity.OrderProduct;
import study.shopbasics.entity.Product;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderSaveRequestProductDetail {

    @NotNull(message = "productId must not be null")
    private Long productId;

    @NotNull(message = "quantity must not be null")
    @DecimalMin(value = "0", message = "quantity must be positive")
    private Integer quantity;

    public OrderProduct toEntity(Product product) {
        return OrderProduct.builder()
                .product(product)
                .quantity(quantity)
                .build();
    }
}
