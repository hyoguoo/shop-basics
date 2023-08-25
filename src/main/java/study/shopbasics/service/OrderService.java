package study.shopbasics.service;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import study.shopbasics.dto.request.OrderSaveRequest;
import study.shopbasics.dto.request.OrderSaveRequestProductDetail;
import study.shopbasics.dto.response.OrderSaveResponse;
import study.shopbasics.entity.OrderInfo;
import study.shopbasics.entity.OrderProduct;
import study.shopbasics.entity.Product;
import study.shopbasics.entity.User;
import study.shopbasics.repository.OrderInfoRepository;

import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class OrderService {

    private final OrderInfoRepository orderInfoRepository;
    private final ProductService productService;
    private final UserService userService;

    @Transactional
    public OrderSaveResponse saveOrder(@Valid OrderSaveRequest orderSaveRequest) {
        User requestUser = userService.findById(orderSaveRequest.getUserId());
        List<OrderProduct> orderProducts = getOrderProducts(orderSaveRequest.getProductDetailAsList());

        OrderInfo orderInfo = orderSaveRequest.toEntity(requestUser, orderProducts);

        return OrderSaveResponse.of(orderInfoRepository.save(orderInfo));
    }

    private List<OrderProduct> getOrderProducts(List<OrderSaveRequestProductDetail> orderSaveRequestProductDetails) {
        return orderSaveRequestProductDetails
                .stream()
                .map(this::processOrderProduct)
                .toList();
    }

    private OrderProduct processOrderProduct(OrderSaveRequestProductDetail orderSaveRequestProductDetail) {
        Product product = productService.reduceProductStock(orderSaveRequestProductDetail.getProductId(), orderSaveRequestProductDetail.getQuantity());

        return orderSaveRequestProductDetail.toEntity(product);
    }
}
