package com.aicitizen.d14.mapper;

import com.aicitizen.d14.dto.RequestDto.AddCitizenRequestDto;
import com.aicitizen.d14.entity.Citizen;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CitizenMapper {

    CitizenMapper INSTANCE= Mappers.getMapper(CitizenMapper.class);

    Citizen toCitizen(final AddCitizenRequestDto addCitizenRequestDto);
}
