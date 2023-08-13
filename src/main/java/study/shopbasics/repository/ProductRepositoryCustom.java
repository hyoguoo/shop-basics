package study.shopbasics.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import study.shopbasics.entity.Product;

public interface ProductRepositoryCustom {

    Page<Product> findWithSearchKeyword(String searchKeyword, Pageable pageable);
}
