package com.elsebaey.restaurant.domain;

import com.elsebaey.restaurant.domain.entities.Address;
import com.elsebaey.restaurant.domain.entities.OperatingHours;
import com.elsebaey.restaurant.domain.entities.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantCreateUpdateRequest {
    private String name;
    private String cuisineType;
    private String contactInformation;
    private Address address;
    private OperatingHours operatingHours;
    private List<String> photoIds;
}
