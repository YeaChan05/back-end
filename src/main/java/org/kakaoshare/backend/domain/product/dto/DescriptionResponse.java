package org.kakaoshare.backend.domain.product.dto;

import lombok.Builder;
import lombok.Getter;
import org.kakaoshare.backend.domain.option.entity.Option;
import org.kakaoshare.backend.domain.product.entity.Product;
import org.kakaoshare.backend.domain.product.entity.ProductDescriptionPhoto;
import org.kakaoshare.backend.domain.product.entity.ProductThumbnail;
import java.util.List;

@Getter
@Builder
public class DescriptionResponse {
    private final Long productId;
    private final String name;
    private final Long price;
    private final String type;
    private final String description;
    private final List<ProductDescriptionPhoto> descriptionPhotos;
    private final String productName;
    private final List<Option> options;
    private final List<ProductThumbnail> productThumbnails;
    private final String brandName;

    public static DescriptionResponse from(final Product product) {
        return DescriptionResponse.builder()
                .productId(product.getProductId())
                .name(product.getName())
                .price(product.getPrice())
                .type(product.getType())
                .description(product.getProductDetail().getDescription())
                .descriptionPhotos(null)//TODO 2024 03 21 16:33:40 : 추후 수정 필요
                .hasPhoto(product.getProductDetail().getHasPhoto())
                .productName(product.getProductDetail().getProductName())
                .options(null)//TODO 2024 03 21 16:33:50 : 추후 수정 필요
                .brandName(product.getBrandName())
                .build();
    }
}
