package study.shopbasics.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shopbasics.entity.Product;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Integer stock;

    public ProductResponse(Long id, String name, String description, BigDecimal price, String imageUrl, Integer stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.stock = stock;
    }

    public static ProductResponse of(final Product product) {
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImageUrl(), product.getStock());
    }
}
