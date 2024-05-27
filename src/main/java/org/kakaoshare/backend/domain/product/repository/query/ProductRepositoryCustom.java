package org.kakaoshare.backend.domain.product.repository.query;

import org.kakaoshare.backend.domain.member.entity.Member;
import org.kakaoshare.backend.domain.product.dto.DescriptionResponse;
import org.kakaoshare.backend.domain.product.dto.DetailResponse;
import org.kakaoshare.backend.domain.product.dto.Product4DisplayDto;
import org.kakaoshare.backend.domain.product.dto.ProductDto;
import org.kakaoshare.backend.domain.search.dto.SimpleBrandProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductRepositoryCustom {
    Page<Product4DisplayDto> findAllByCategoryId(Long categoryId, Pageable pageable, final String providerId);
    Page<ProductDto> findAllByBrandId(final Long brandId, final Pageable pageable);
    Page<ProductDto> findAllByProductIds(final List<Long> productIds, final Pageable pageable);
    DescriptionResponse findProductWithDetailsAndPhotosWithoutMember(Long productId);
    DescriptionResponse findProductWithDetailsAndPhotosWithMember(Long productId, Member member);
    DetailResponse findProductDetailWithoutMember(Long productId);
    DetailResponse findProductDetailWithMember(Long productId, Member member);
    Page<Product4DisplayDto> findBySearchConditions(final String keyword, final Integer minPrice, final Integer maxPrice, final List<String> categories, final Pageable pageable,final String providerId);
    
    Page<SimpleBrandProductDto> findBySearchConditionsGroupByBrand(String keyword,
                                                                   Pageable pageable,
                                                                   String providerId);
    
    Map<Long, Long> findAllPriceByIdsGroupById(final List<Long> productIds);
    Map<Long, String> findAllNameByIdsGroupById(final List<Long> productIds);
}
