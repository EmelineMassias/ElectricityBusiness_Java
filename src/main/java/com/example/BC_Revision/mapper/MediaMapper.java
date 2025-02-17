package com.example.BC_Revision.mapper;

import com.example.BC_Revision.dto.MediaDto;
import com.example.BC_Revision.model.Media;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediaMapper {

    Media toEntity(MediaDto mediaDto);

    @Mapping(target = "borneId", source = "bornes")
    MediaDto toDto(Media media);
}
