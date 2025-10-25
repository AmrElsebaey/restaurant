package com.elsebaey.restaurant.mapper;

import com.elsebaey.restaurant.domain.RestaurantCreateUpdateRequest;
import com.elsebaey.restaurant.domain.dtos.GeoPointDto;
import com.elsebaey.restaurant.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.elsebaey.restaurant.domain.dtos.RestaurantDto;
import com.elsebaey.restaurant.domain.dtos.RestaurantSummaryDto;
import com.elsebaey.restaurant.domain.entities.Restaurant;
import com.elsebaey.restaurant.domain.entities.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequest(RestaurantCreateUpdateRequestDto dto);

    @Mapping(source = "reviews", target = "totalReviews", qualifiedByName = "populateTotalReviews")
    RestaurantDto toRestaurantDto(Restaurant restaurant);

    @Mapping(source = "reviews", target = "totalReviews", qualifiedByName = "populateTotalReviews")
    RestaurantSummaryDto toRestaurantSummaryDto(Restaurant restaurant);

    @Named("populateTotalReviews")
     default Integer populateTotalReviews(List<Review> reviews) {
        return reviews.size();
    }

    @Mapping(target = "latitude", source = "lat")
    @Mapping(target = "longitude", source = "lon")
    GeoPointDto toGeoPointDto(GeoPoint geoPoint);
}
