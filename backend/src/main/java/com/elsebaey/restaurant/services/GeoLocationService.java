package com.elsebaey.restaurant.services;

import com.elsebaey.restaurant.domain.GeoLocation;
import com.elsebaey.restaurant.domain.entities.Address;

public interface GeoLocationService {
    GeoLocation getLocate(Address address);
}
