package com.example.BC_Revision.controller.rest;

import com.example.BC_Revision.dto.ReservationDto;
import com.example.BC_Revision.model.Reservation;
import com.example.BC_Revision.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/reservations")
public class ReservationControllerRest {
    private ReservationService reservationService;
    public ReservationControllerRest(ReservationService reservationService){
        this.reservationService = reservationService;
    }

    //Méthode Get #1
    @GetMapping("")
    public List<ReservationDto> getAllReservations() {

        return reservationService.getAllReservations();
    }

    //Méthode Get #2
    @GetMapping("/{id}")
    @ResponseStatus(code= HttpStatus.OK)
    public ReservationDto getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id);
    }

    //Méthode Post
    @PostMapping("")
    @ResponseStatus(code=HttpStatus.CREATED)
    public Reservation saveReservation(@Valid @RequestBody ReservationDto reservationDto,
                                       BindingResult result) {
        return reservationService
                .saveReservation(reservationDto);
    }

    //Méthode Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteReservation(@PathVariable Long id) {
        reservationService.deleteReservation(id);
    }

//

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable Long id,
                                         @Valid @RequestBody ReservationDto reservationDto) {
        reservationDto.setId(id);
        return reservationService
                .saveReservation(reservationDto);
    }

    //@ExceptionHandler(BorneInexistanteException.class)
//        @ResponseStatus(code=HttpStatus.NOT_FOUND)
//        public String traiterBorneInexistanteException(Exception e) {
//            return e.getMessage();
//        }

    //    @ExceptionHandler(ParasolDejaExistantException.class)
//    @ResponseStatus(code=HttpStatus.CONFLICT)
//    public String traiterParasolDejaPresentException(Exception e) {
//        return e.getMessage();
//    }

}
