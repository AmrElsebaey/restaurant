package com.elsebaey.restaurant.services.impl;

import com.elsebaey.restaurant.domain.ReviewCreateUpdateRequest;
import com.elsebaey.restaurant.domain.entities.Photo;
import com.elsebaey.restaurant.domain.entities.Restaurant;
import com.elsebaey.restaurant.domain.entities.Review;
import com.elsebaey.restaurant.domain.entities.User;
import com.elsebaey.restaurant.exceptions.RestaurantNotFoundException;
import com.elsebaey.restaurant.exceptions.ReviewNotAllowedException;
import com.elsebaey.restaurant.repositories.RestaurantRepository;
import com.elsebaey.restaurant.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review) {
        Restaurant restaurant = getRestaurantOrThrow(restaurantId);

        boolean hasExistingReview = restaurant.getReviews().stream()
                .anyMatch(r -> r.getWrittenBy().getId().equals(author.getId()));

        if (hasExistingReview) {
            throw new ReviewNotAllowedException("User has already reviewed this restaurant");
        }

        LocalDateTime now = LocalDateTime.now();
        List<Photo> photos = review.getPhotoIds().stream()
                .map(photoUrl -> Photo.builder()
                        .url(photoUrl)
                        .uploadDate(now)
                        .build()

                ).toList();

        String reviewId = UUID.randomUUID().toString();
        Review reviewToCreate = Review.builder()
                .id(reviewId)
                .content(review.getContent())
                .rating(review.getRating())
                .photos(photos)
                .datePosted(now)
                .lastEdited(now)
                .writtenBy(author)
                .build();

        restaurant.getReviews().add(reviewToCreate);

        updateRestaurantAverageRating(restaurant);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);

        return savedRestaurant.getReviews().stream()
                .filter(r -> r.getId().equals(reviewId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Review with id not found" + reviewId));
    }

    private Restaurant getRestaurantOrThrow(String restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant with id not found" + restaurantId));
    }

    private void updateRestaurantAverageRating (Restaurant restaurant) {
        List<Review> reviews = restaurant.getReviews();

        if (reviews.isEmpty()) {
            restaurant.setAverageRating(0.0f);
        } else {
            double averageRating = reviews.stream()
                    .mapToDouble(Review::getRating)
                    .average()
                    .orElse(0.0);
            restaurant.setAverageRating((float) averageRating);
        }
    }
}
