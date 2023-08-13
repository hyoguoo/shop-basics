package study.shopbasics.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import study.shopbasics.entity.Product;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProductPageResponse {

    private boolean hasNext;
    private long totalElements;
    private List<ProductResponse> items;

    public ProductPageResponse(final boolean hasNext, final long totalElements, final List<ProductResponse> items) {
        this.hasNext = hasNext;
        this.totalElements = totalElements;
        this.items = items;
    }

    public static ProductPageResponse of(final Page<Product> products) {
        List<ProductResponse> productRespons = products.getContent()
                .stream()
                .map(ProductResponse::of)
                .toList();

        return new ProductPageResponse(products.hasNext(), products.getTotalElements(), productRespons);
    }
}
