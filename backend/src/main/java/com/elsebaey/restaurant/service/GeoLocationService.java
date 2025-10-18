package com.elsebaey.restaurant.service;

import com.elsebaey.restaurant.domain.GeoLocation;
import com.elsebaey.restaurant.domain.entities.Address;

public interface GeoLocationService {
    GeoLocation getLocate(Address address);
}
