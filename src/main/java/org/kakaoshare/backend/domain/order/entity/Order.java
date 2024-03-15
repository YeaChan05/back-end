package org.kakaoshare.backend.domain.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import org.kakaoshare.backend.domain.base.entity.BaseTimeEntity;
import org.kakaoshare.backend.domain.funding.entity.FundingDetail;
import org.kakaoshare.backend.domain.gift.entity.Gift;
import org.kakaoshare.backend.domain.member.entity.Member;
import org.kakaoshare.backend.domain.payment.entity.Payment;
import org.kakaoshare.backend.domain.product.entity.Product;

import java.util.List;

import static org.kakaoshare.backend.domain.order.entity.OrderStatus.COMPLETE_PAYMENT;


@Entity
@Getter
@Table(name = "orders")
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;

    @Column(nullable = false)
    private Integer stockQuantity;

    @Column(nullable = false, length = 50)
    private String orderNumber;

    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = COMPLETE_PAYMENT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "funding_detail_id")
    private FundingDetail fundingDetail;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToMany(mappedBy = "order")
    private List<Gift> gifts;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    protected Order() {
    }

    @Builder
    public Order(final Integer stockQuantity, final String orderNumber, final Member member, final Product product, final Payment payment) {
        this.stockQuantity = stockQuantity;
        this.orderNumber = orderNumber;
        this.member = member;
        this.product = product;
        this.payment = payment;
    }
}
