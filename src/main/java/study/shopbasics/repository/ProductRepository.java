package study.shopbasics.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.shopbasics.entity.Product;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    Optional<Product> findByNameAndDescription(String name, String description);
}
