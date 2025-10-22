package com.elsebaey.restaurant.services;

import com.elsebaey.restaurant.domain.RestaurantCreateUpdateRequest;
import com.elsebaey.restaurant.domain.entities.Restaurant;

public interface RestaurantService {
    Restaurant createRestaurant(RestaurantCreateUpdateRequest request);
}
