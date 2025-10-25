package com.elsebaey.restaurant.services;

import com.elsebaey.restaurant.domain.ReviewCreateUpdateRequest;
import com.elsebaey.restaurant.domain.entities.Review;
import com.elsebaey.restaurant.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review);

    Page<Review> listReviews(String restaurantId, Pageable pageable);

}
