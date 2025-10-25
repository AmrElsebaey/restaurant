package com.elsebaey.restaurant.mapper;

import com.elsebaey.restaurant.domain.ReviewCreateUpdateRequest;
import com.elsebaey.restaurant.domain.dtos.ReviewCreateUpdateRequestDto;
import com.elsebaey.restaurant.domain.dtos.ReviewDto;
import com.elsebaey.restaurant.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface ReviewMapper {

    ReviewCreateUpdateRequest toReviewCreateUpdateRequest(ReviewCreateUpdateRequestDto dto);

    ReviewDto toReviewDto(Review review);
}
