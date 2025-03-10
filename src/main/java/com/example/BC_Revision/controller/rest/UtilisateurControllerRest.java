package com.example.BC_Revision.controller.rest;

import com.example.BC_Revision.dto.LoginRequest;
import com.example.BC_Revision.dto.UtilisateurDto;
import com.example.BC_Revision.model.Utilisateur;
import com.example.BC_Revision.service.UtilisateurService;
import com.example.BC_Revision.service.impl.TokenService;
import jakarta.validation.Valid;
import org.antlr.v4.runtime.Token;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("api/utilisateurs")
public class UtilisateurControllerRest {
    private UtilisateurService utilisateurService;
    //private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private PasswordEncoder passwordEncoder;

    public UtilisateurControllerRest(UtilisateurService utilisateurService, TokenService tokenService,
                                     PasswordEncoder passwordEncoder/*, AuthenticationManager authenticationManager*/){
        this.utilisateurService = utilisateurService;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        //this.authenticationManager = authenticationManager;

    }

    //Méthode Get #1
    @GetMapping("")
    public List<UtilisateurDto> getAllUtilisateurs() {

        return utilisateurService.getAllUtilisateurs();
    }

    //Méthode Get #2
    @GetMapping("/{id}")
    @ResponseStatus(code= HttpStatus.OK)
    public UtilisateurDto getUtilisateurById(@PathVariable Long id) {
        return utilisateurService.getUtilisateurById(id);
    }

    //Méthode Post
    @PostMapping("")
    @ResponseStatus(code=HttpStatus.CREATED)
    public Utilisateur saveUtilisateur(@Valid @RequestBody UtilisateurDto utilisateurDto,
                                       BindingResult result) {
        utilisateurDto.setMotDePasse(passwordEncoder.encode(utilisateurDto.getMotDePasse()));
        return utilisateurService
                .saveUtilisateur(utilisateurDto);
    }

    //Méthode Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteUtilisateur(@PathVariable Long id) {

        utilisateurService.deleteUtilisateur(id);
    }

//

    @PutMapping("/{id}")
    public Utilisateur updateUtilisateur(@PathVariable Long id,
                                         @Valid @RequestBody UtilisateurDto utilisateurDto) {
        utilisateurDto.setId(id);
        return utilisateurService.saveUtilisateur(utilisateurDto);
    }

    /*@PostMapping("/login")
    public ResponseEntity<String> mlogin(@RequestBody LoginRequest loginRequest){
        // try catch permet de gérer l'erreur
        try {
            // .authenticate va essayer d'authentifier l'objet en paramètre
            Authentication authenticationObject = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword());
            authenticationObject =  authenticationManager.authenticate(authenticationObject);

            String token = tokenService.generateToken(authenticationObject);

            return ResponseEntity.ok(token);
        } catch (BadCredentialsException ex){
            return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }*/

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
