package study.shopbasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shopbasics.entity.UserLike;

import java.util.Optional;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {

    Optional<UserLike> findByUserIdAndProductId(Long userId, Long productId);
}
