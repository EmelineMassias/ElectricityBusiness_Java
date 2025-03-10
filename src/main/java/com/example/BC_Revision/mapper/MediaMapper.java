package com.example.BC_Revision.mapper;

import com.example.BC_Revision.dto.MediaDto;
import com.example.BC_Revision.model.Media;
import com.example.BC_Revision.model.Borne;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface MediaMapper {

    Media toEntity(MediaDto mediaDto);

    @Mapping(target = "borneId", source = "borne")
    MediaDto toDto(Media media);

    // Add this mapping method to convert Borne to Long
    default Long map(Borne borne) {
        return borne != null ? borne.getId() : null;
    }
}

