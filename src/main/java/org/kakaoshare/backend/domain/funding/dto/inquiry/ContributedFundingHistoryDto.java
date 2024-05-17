package org.kakaoshare.backend.domain.funding.dto.inquiry;

import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record ContributedFundingHistoryDto(Long fundingId,
                                           Long fundingDetailId,
                                           LocalDateTime attributedAt,
                                           String creatorName,
                                           String status) {
    @QueryProjection
    public ContributedFundingHistoryDto {
    }
}
