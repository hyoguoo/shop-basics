package study.shopbasics.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import study.shopbasics.dto.request.UserLikeRequest;
import study.shopbasics.entity.Product;
import study.shopbasics.entity.User;
import study.shopbasics.repository.UserLikeRepository;

@Service
@Validated
@RequiredArgsConstructor
public class UserLikeService {

    private final UserLikeRepository userLikeRepository;
    private final ProductService productService;
    private final UserService userService;

    @Transactional
    public void saveUserLike(@Valid UserLikeRequest userLikeRequest) {
        User requestUser = userService.findById(userLikeRequest.getUserId());
        Product findProduct = productService.findById(userLikeRequest.getProductId());

        if (userLikeRepository.findByUserIdAndProductId(userLikeRequest.getUserId(), userLikeRequest.getProductId()).isEmpty()) {
            userLikeRepository.save(userLikeRequest.toEntity(requestUser, findProduct));
        }
    }

    @Transactional
    public void deleteUserLike(@Valid UserLikeRequest userLikeRequest) {
        userLikeRepository.findByUserIdAndProductId(userLikeRequest.getUserId(), userLikeRequest.getProductId())
                .ifPresent(userLikeRepository::delete);
    }
}
