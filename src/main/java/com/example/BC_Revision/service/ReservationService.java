package com.example.BC_Revision.service;

import com.example.BC_Revision.dto.ReservationDto;
import com.example.BC_Revision.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationService {

    Reservation saveReservation(ReservationDto reservationDto);

    List<ReservationDto> getAllReservations();

    ReservationDto getReservationById(Long id);

    void deleteReservation(Long id);

}
