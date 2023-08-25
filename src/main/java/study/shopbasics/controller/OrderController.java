package study.shopbasics.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.shopbasics.dto.request.OrderSaveRequest;
import study.shopbasics.dto.response.OrderSaveResponse;
import study.shopbasics.service.OrderService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderSaveResponse saveOrder(@RequestBody @Valid OrderSaveRequest orderSaveRequest) {
        return this.orderService.saveOrder(orderSaveRequest);
    }
}
