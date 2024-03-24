package org.kakaoshare.backend.domain.funding.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.kakaoshare.backend.domain.base.entity.BaseTimeEntity;
import org.kakaoshare.backend.domain.member.entity.Member;
import org.kakaoshare.backend.domain.product.entity.Product;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Funding extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundingId;

    @Column
    private String status;

    @Column(nullable = false)
    private LocalDate expiredAt;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal goalAmount;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal accumulateAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @OneToMany(mappedBy = "funding")
    private List<FundingDetail> fundingDetails;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    public Funding(Member member, Product product, BigDecimal goalAmount, LocalDate expiredAt) {
        this.member = member;
        this.product = product;
        this.goalAmount = goalAmount;
        this.expiredAt = expiredAt;
        this.accumulateAmount = BigDecimal.ZERO; // 초기값 설정
        this.status = "ACTIVE"; // 초기 상태 설정
    }
}
