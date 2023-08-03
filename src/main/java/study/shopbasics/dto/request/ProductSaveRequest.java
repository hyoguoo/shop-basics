package study.shopbasics.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shopbasics.entity.Product;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductSaveRequest {

    @NotNull(message = "name must not be null")
    private String name;

    @NotNull(message = "price must not be null")
    @DecimalMin(value = "0", message = "price must be positive")
    private BigDecimal price;

    @NotNull(message = "description must not be null")
    private String description;

    @NotNull(message = "imageUrl must not be null")
    private String imageUrl;

    @NotNull(message = "stock must not be null")
    @DecimalMin(value = "0", message = "stock must be positive")
    private Integer stock;

    public ProductSaveRequest(String name, BigDecimal price, String description, String imageUrl, Integer stock) {
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public Product toEntity() {
        return Product.builder()
                .name(name)
                .price(price)
                .description(description)
                .imageUrl(imageUrl)
                .stock(stock)
                .build();
    }
}
