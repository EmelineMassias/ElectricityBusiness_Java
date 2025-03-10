package com.example.BC_Revision.mapper;

import com.example.BC_Revision.dto.ReservationDto;
import com.example.BC_Revision.model.Reservation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ReservationMapper {
    @Mapping(target="utilisateurId", source="utilisateur.id")
    @Mapping(target="borneId", source="borne.id")
    ReservationDto toDto(Reservation reservation);
    Reservation toEntity(ReservationDto reservationDto);

}
