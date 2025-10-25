package com.elsebaey.restaurant.services;

import com.elsebaey.restaurant.domain.ReviewCreateUpdateRequest;
import com.elsebaey.restaurant.domain.entities.Review;
import com.elsebaey.restaurant.domain.entities.User;

public interface ReviewService {

    Review createReview(User author, String restaurantId, ReviewCreateUpdateRequest review);

}
