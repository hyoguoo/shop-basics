package study.shopbasics.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shopbasics.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProductFindDetailResponse {

    private Long id;
    private String name;
    private BigDecimal price;
    private String description;
    private String imageUrl;
    private Integer stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public ProductFindDetailResponse(Long id, String name, BigDecimal price, String description, String imageUrl, Integer stock, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
        this.stock = stock;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static ProductFindDetailResponse of(final Product productResponse) {
        return new ProductFindDetailResponse(productResponse.getId(), productResponse.getName(), productResponse.getPrice(), productResponse.getDescription(), productResponse.getImageUrl(), productResponse.getStock(), productResponse.getCreatedAt(), productResponse.getUpdatedAt());
    }
}
