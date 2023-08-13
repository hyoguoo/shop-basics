package study.shopbasics.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import study.shopbasics.entity.Product;

import java.util.List;

import static study.shopbasics.entity.QProduct.product;

@AllArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Product> findWithSearchKeyword(String searchKeyword, Pageable pageable) {
        BooleanExpression expression = nameOrDescriptionContainsIgnoreCase(searchKeyword);

        List<Product> products = jpaQueryFactory
                .selectFrom(product)
                .where(expression)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Product> countQuery = jpaQueryFactory
                .selectFrom(product)
                .where(expression);

        return PageableExecutionUtils.getPage(products, pageable, countQuery::fetchCount);
    }

    private BooleanExpression nameOrDescriptionContainsIgnoreCase(String keyword) {
        return product.name.containsIgnoreCase(keyword)
                .or(product.description.containsIgnoreCase(keyword));
    }
}
