package study.shopbasics.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_info")
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "orderInfo", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderProduct> orderProducts = new ArrayList<>();

    @Builder.Default
    @Column(name = "order_number", nullable = false)
    private String orderNumber = getUniqueOrderNumber();

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;

    public void addOrderProducts(List<OrderProduct> orderProducts) {
        for (OrderProduct orderProduct : orderProducts) {
            this.orderProducts.add(orderProduct);
            orderProduct.setOrderInfo(this);
        }
    }

    private static String getUniqueOrderNumber() {
        return UUID.randomUUID().toString();
    }
}
