package com.example.BC_Revision.mapper;

import com.example.BC_Revision.dto.UtilisateurDto;
import com.example.BC_Revision.model.Borne;
import com.example.BC_Revision.model.Reservation;
import com.example.BC_Revision.model.Utilisateur;
import org.mapstruct.Mapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import java.util.List;
import java.util.stream.Collectors;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UtilisateurMapper {
    @Mapping(target="bornesId", source="bornes")
    @Mapping(target="reservationsId", source="reservations")
    UtilisateurDto toDto(Utilisateur utilisateur);
    Utilisateur toEntity(UtilisateurDto utilisateurDto);

    default List<Long> bornesToBorneIds(List<Borne> bornes) {
        if (bornes == null) {
            return null;
        }
        return bornes.stream()
                .map(Borne::getId)
                .collect(Collectors.toList());
    }

    default List<Long> reservationsToReservationIds(List<Reservation> reservations) {
        if (reservations == null) {
            return null;
        }
        return reservations.stream()
                .map(Reservation::getId)
                .collect(Collectors.toList());
    }
}

