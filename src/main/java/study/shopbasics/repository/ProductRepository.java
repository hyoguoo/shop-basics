package study.shopbasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shopbasics.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
