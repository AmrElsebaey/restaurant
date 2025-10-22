package com.elsebaey.restaurant.services.impl;

import com.elsebaey.restaurant.domain.GeoLocation;
import com.elsebaey.restaurant.domain.RestaurantCreateUpdateRequest;
import com.elsebaey.restaurant.domain.entities.Address;
import com.elsebaey.restaurant.domain.entities.Photo;
import com.elsebaey.restaurant.domain.entities.Restaurant;
import com.elsebaey.restaurant.repositories.RestaurantRepository;
import com.elsebaey.restaurant.services.GeoLocationService;
import com.elsebaey.restaurant.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final GeoLocationService geoLocationService;

    @Override
    public Restaurant createRestaurant(RestaurantCreateUpdateRequest request) {
        Address address = request.getAddress();
        GeoLocation geoLocation = geoLocationService.getLocate(address);
        GeoPoint geoPoint = new GeoPoint(geoLocation.getLatitude(), geoLocation.getLongitude());

        List<String> photoIds = request.getPhotoIds();
        List<Photo> photos = photoIds.stream()
                .map(photoUrl -> Photo.builder()
                        .url(photoUrl)
                        .uploadDate(LocalDateTime.now())
                        .build()
                ).toList();

        Restaurant restaurant = Restaurant.builder()
                .name(request.getName())
                .cuisineType(request.getCuisineType())
                .contactInformation(request.getContactInformation())
                .geoLocation(geoPoint)
                .operatingHours(request.getOperatingHours())
                .averageRating(0f)
                .photos(photos)
                .build();

        return restaurantRepository.save(restaurant);
    }
}
