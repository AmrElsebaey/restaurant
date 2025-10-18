package com.elsebaey.restaurant.mapper;

import com.elsebaey.restaurant.domain.RestaurantCreateUpdateRequest;
import com.elsebaey.restaurant.domain.dtos.GeoPointDto;
import com.elsebaey.restaurant.domain.dtos.RestaurantCreateUpdateRequestDto;
import com.elsebaey.restaurant.domain.dtos.RestaurantDto;
import com.elsebaey.restaurant.domain.entities.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.elasticsearch.core.geo.GeoPoint;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RestaurantMapper {
    RestaurantCreateUpdateRequest toRestaurantCreateUpdateRequest(RestaurantCreateUpdateRequestDto dto);
    RestaurantDto toRestaurantDto(Restaurant restaurant);

    @Mapping(target = "latitude", source = "lat")
    @Mapping(target = "longitude", source = "lon")
    GeoPointDto toGeoPointDto(GeoPoint geoPoint);
}
