package com.example.BC_Revision.mapper;

import com.example.BC_Revision.dto.LieuDto;
import com.example.BC_Revision.model.Borne;
import com.example.BC_Revision.model.Lieu;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

import java.util.List;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING)
public interface LieuMapper {
    @Mapping(target = "bornesId", source="bornes")
    LieuDto toDto(Lieu lieu);
    Lieu toEntity(LieuDto lieuDto);


}
