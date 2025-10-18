package com.elsebaey.restaurant.mapper;

import com.elsebaey.restaurant.domain.dtos.PhotoDto;
import com.elsebaey.restaurant.domain.entities.Photo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PhotoMapper {

    PhotoDto toDto(Photo photo);
}
