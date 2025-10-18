package com.elsebaey.restaurant.service.impl;

import com.elsebaey.restaurant.domain.GeoLocation;
import com.elsebaey.restaurant.domain.entities.Address;
import com.elsebaey.restaurant.service.GeoLocationService;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomLondonGeoLocationService implements GeoLocationService {

    private static final float MIN_LATITUDE = 51.28F;
    private static final float MAX_LATITUDE = 51.686F;
    private static final float MIN_LONGITUDE = -0.489F;
    private static final float MAX_LONGITUDE = 0.236F;

    @Override
    public GeoLocation getLocate(Address address) {
        Random random = new Random();
        double latitude = MIN_LATITUDE + (MAX_LATITUDE - MIN_LATITUDE) * random.nextDouble();
        double longitude = MIN_LONGITUDE + (MAX_LONGITUDE - MIN_LONGITUDE) * random.nextDouble();
        return GeoLocation.builder()
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }
}
