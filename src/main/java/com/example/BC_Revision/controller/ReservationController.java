package com.example.BC_Revision.controller;

import com.example.BC_Revision.service.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ReservationController {
    private ReservationService reservationService;
}
