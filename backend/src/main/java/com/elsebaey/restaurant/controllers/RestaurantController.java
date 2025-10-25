package com.elsebaey.restaurant.controllers;

import com.elsebaey.restaurant.domain.RestaurantCreateUpdateRequest;
import com.elsebaey.restaurant.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.elsebaey.restaurant.domain.dtos.RestaurantDto;
import com.elsebaey.restaurant.domain.dtos.RestaurantSummaryDto;
import com.elsebaey.restaurant.domain.entities.Restaurant;
import com.elsebaey.restaurant.mapper.RestaurantMapper;
import com.elsebaey.restaurant.services.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    @PostMapping
    public ResponseEntity<RestaurantDto> createRestaurant(
            @Valid @RequestBody RestaurantCreateUpdateRequestDto request
    ) {
        RestaurantCreateUpdateRequest restaurantCreateUpdateRequest = restaurantMapper.toRestaurantCreateUpdateRequest(request);
        Restaurant restaurant = restaurantService.createRestaurant(restaurantCreateUpdateRequest);
        return ResponseEntity.ok(restaurantMapper.toRestaurantDto(restaurant));
    }

    @GetMapping
    public Page<RestaurantSummaryDto> searchRestaurants (
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Float minRating,
            @RequestParam(required = false) Float latitude,
            @RequestParam(required = false) Float longitude,
            @RequestParam(required = false) Float radius,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Page<Restaurant> searchResults = restaurantService.searchRestaurants(
                q, minRating,
                latitude, longitude,
                radius, PageRequest.of(page - 1, size));
        return searchResults.map(restaurantMapper::toRestaurantSummaryDto);
    }

    @GetMapping("/{restaurant_id}")
    public ResponseEntity<RestaurantDto> getRestaurant(@PathVariable(name = "restaurant_id") String restaurantId) {
        return restaurantService.getRestaurant(restaurantId)
                .map(restaurantMapper::toRestaurantDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{restaurant_id}")
    public ResponseEntity<RestaurantDto> updateRestaurant(
            @PathVariable(name = "restaurant_id") String restaurantId,
            @Valid @RequestBody RestaurantCreateUpdateRequestDto request
    ) {
        RestaurantCreateUpdateRequest restaurantCreateUpdateRequest = restaurantMapper.toRestaurantCreateUpdateRequest(request);
        Restaurant restaurant = restaurantService.updateRestaurant(restaurantId, restaurantCreateUpdateRequest);
        return ResponseEntity.ok(restaurantMapper.toRestaurantDto(restaurant));
    }
    
    @DeleteMapping("/{restaurant_id}")
    public ResponseEntity<Void> deleteRestaurant (@PathVariable(name = "restaurant_id") String restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return ResponseEntity.noContent().build();
    }
}
