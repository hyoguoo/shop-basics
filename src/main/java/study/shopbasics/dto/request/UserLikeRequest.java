package study.shopbasics.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import study.shopbasics.entity.Product;
import study.shopbasics.entity.User;
import study.shopbasics.entity.UserLike;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserLikeRequest {

    // TODO: user id will be replaced with token
    @NotNull(message = "userId must not be null")
    private Long userId;

    @NotNull(message = "productId must not be null")
    private Long productId;

    public UserLike toEntity(User user, Product product) {
        return UserLike.builder()
                .user(user)
                .product(product)
                .build();
    }
}
