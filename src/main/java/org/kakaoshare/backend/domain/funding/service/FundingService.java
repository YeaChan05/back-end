package org.kakaoshare.backend.domain.funding.service;

import lombok.RequiredArgsConstructor;
import org.kakaoshare.backend.common.dto.PageResponse;
import org.kakaoshare.backend.domain.funding.dto.FundingResponse;
import org.kakaoshare.backend.domain.funding.dto.FundingSliceResponse;
import org.kakaoshare.backend.domain.funding.dto.ProgressResponse;
import org.kakaoshare.backend.domain.funding.dto.RegisterRequest;
import org.kakaoshare.backend.domain.funding.dto.RegisterResponse;
import org.kakaoshare.backend.domain.funding.dto.preview.request.FundingPreviewRequest;
import org.kakaoshare.backend.domain.funding.dto.preview.request.FundingProductDto;
import org.kakaoshare.backend.domain.funding.dto.preview.response.FundingPreviewResponse;
import org.kakaoshare.backend.domain.funding.entity.Funding;
import org.kakaoshare.backend.domain.funding.entity.FundingStatus;
import org.kakaoshare.backend.domain.funding.exception.FundingErrorCode;
import org.kakaoshare.backend.domain.funding.exception.FundingException;
import org.kakaoshare.backend.domain.funding.repository.FundingRepository;
import org.kakaoshare.backend.domain.member.entity.Member;
import org.kakaoshare.backend.domain.member.repository.MemberRepository;
import org.kakaoshare.backend.domain.product.dto.ProductDto;
import org.kakaoshare.backend.domain.product.entity.Product;
import org.kakaoshare.backend.domain.product.exception.ProductErrorCode;
import org.kakaoshare.backend.domain.product.exception.ProductException;
import org.kakaoshare.backend.domain.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class FundingService {
    private final FundingRepository fundingRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public RegisterResponse registerFundingItem(Long productId, String providerId, RegisterRequest request) {
        Product product = findProductById(productId);
        Member member = findMemberByProviderId(providerId);

        Optional<FundingResponse> existingFunding = fundingRepository.findByProductIdAndMemberId(productId,
                member.getMemberId());
        if (existingFunding.isPresent() && "Active".equals(existingFunding.get().getStatus())) {
            throw new IllegalStateException("There is already an active funding for this product and user.");
        }

        Funding funding = request.toEntity(member, product);
        funding = fundingRepository.save(funding);

        return RegisterResponse.from(funding);
    }

    public ProgressResponse getFundingProgress(Long fundingId, String providerId) {
        Member member = findMemberByProviderId(providerId);
        Funding funding = fundingRepository.findByIdAndMemberId(fundingId, member.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid fundingId"));

        return ProgressResponse.from(funding);
    }

    public PageResponse<?> getMyFilteredFundingProducts(String providerId, FundingStatus status,
                                                     Pageable pageable) {
        Member member = findMemberByProviderId(providerId);
        Page<FundingResponse> fundingResponses = fundingRepository.findFundingByMemberIdAndStatusWithPage(
                member.getMemberId(), status, pageable);


        return PageResponse.from(fundingResponses);
    }


    public FundingPreviewResponse preview(final FundingPreviewRequest fundingPreviewRequest) {
        final Long fundingId = fundingPreviewRequest.fundingId();
        final FundingProductDto fundingProductDto = findFundingProductDtoById(fundingId);
        final Long productId = fundingProductDto.productId();
        final ProductDto productDto = findProductDtoByProductId(productId);
        return FundingPreviewResponse.of(productDto, fundingProductDto);
    }

    private Member findMemberByProviderId(String providerId) {
        return memberRepository.findMemberByProviderId(providerId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid providerId"));
    }

    private Product findProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
    }

    private FundingProductDto findFundingProductDtoById(final Long fundingId) {
        return fundingRepository.findAccumulateAmountAndProductIdById(fundingId)
                .orElseThrow(() -> new FundingException(FundingErrorCode.NOT_FOUND));
    }

    private ProductDto findProductDtoByProductId(final Long productId) {
        return productRepository.findProductDtoById(productId)
                .orElseThrow(() -> new ProductException(ProductErrorCode.NOT_FOUND));
    }
}
