package study.shopbasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shopbasics.entity.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
