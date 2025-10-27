package com.elsebaey.restaurant.controllers;

import com.elsebaey.restaurant.domain.ReviewCreateUpdateRequest;
import com.elsebaey.restaurant.domain.dtos.ReviewCreateUpdateRequestDto;
import com.elsebaey.restaurant.domain.dtos.ReviewDto;
import com.elsebaey.restaurant.domain.entities.Review;
import com.elsebaey.restaurant.domain.entities.User;
import com.elsebaey.restaurant.mapper.ReviewMapper;
import com.elsebaey.restaurant.services.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants/{restaurantId}/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(
            @PathVariable String restaurantId,
            @Valid @RequestBody ReviewCreateUpdateRequestDto review,
            @AuthenticationPrincipal Jwt jwt
            ) {
        ReviewCreateUpdateRequest reviewCreateUpdateRequest = reviewMapper.toReviewCreateUpdateRequest(review);

        User user = jwtToUser(jwt);

        Review createdReview = reviewService.createReview(user, restaurantId, reviewCreateUpdateRequest);

        return ResponseEntity.ok(reviewMapper.toReviewDto(createdReview));
    }

    @GetMapping
    public Page<ReviewDto> listReviews(
            @PathVariable String restaurantId,
            @PageableDefault(
                    size = 20,
                    page = 0,
                    sort = "datePosted",
                    direction = Sort.Direction.DESC) Pageable pageable
            ) {
        return reviewService.listReviews(restaurantId, pageable)
                .map(reviewMapper::toReviewDto);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(
            @PathVariable String reviewId,
            @PathVariable String restaurantId
            ) {
        return reviewService.getReview(restaurantId, reviewId)
                .map(reviewMapper::toReviewDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.noContent().build());
    }

    private User jwtToUser(Jwt jwt) {
        return User.builder()
                .id(jwt.getSubject())
                .username(jwt.getClaimAsString("preferred_username"))
                .givenName(jwt.getClaimAsString("given_name"))
                .familyName(jwt.getClaimAsString("family_name"))
                .build();
    }
}
