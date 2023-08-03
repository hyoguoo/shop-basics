package study.shopbasics.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shopbasics.entity.Product;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductSaveResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private Integer stock;

    public ProductSaveResponse(Long id, String name, BigDecimal price, String description, String imageUrl, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public static ProductSaveResponse of(final Product product) {
        return new ProductSaveResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getImageUrl(),
                product.getStock()
        );
    }
}
