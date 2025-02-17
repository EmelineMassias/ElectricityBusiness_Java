package com.example.BC_Revision.controller;

import com.example.BC_Revision.service.UtilisateurService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class UtilisateurController {
    private UtilisateurService utilisateurService;
}
