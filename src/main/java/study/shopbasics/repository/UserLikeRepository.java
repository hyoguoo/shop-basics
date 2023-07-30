package study.shopbasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shopbasics.entity.UserLike;

public interface UserLikeRepository extends JpaRepository<UserLike, Long> {
}
