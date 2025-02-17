package com.example.BC_Revision.mapper;

import com.example.BC_Revision.dto.UtilisateurDto;
import com.example.BC_Revision.model.Utilisateur;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UtilisateurMapper {
    @Mapping(target="borneId", source="borne.id")
    @Mapping(target="reservationId", source="reservation.id")
    UtilisateurDto toDto(Utilisateur utilisateur);
    Utilisateur toEntity(UtilisateurDto utilisateurDto);

}
