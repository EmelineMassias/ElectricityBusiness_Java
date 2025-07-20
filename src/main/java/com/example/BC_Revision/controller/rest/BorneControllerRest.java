package com.example.BC_Revision.controller.rest;

import com.example.BC_Revision.dto.BorneDto;
import com.example.BC_Revision.mapper.BorneMapper;
import com.example.BC_Revision.model.Borne;
import com.example.BC_Revision.service.BorneService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import com.example.BC_Revision.dto.BorneSearchCriteriaDto;

@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
@RestController
@Validated
@RequestMapping("/bornes")
public class BorneControllerRest {

        private BorneService borneService;
        private BorneMapper borneMapper;
        public BorneControllerRest(BorneService borneService, BorneMapper borneMapper){
                this.borneService = borneService;
                this.borneMapper = borneMapper;
        }

        //Méthode Get #1
        @GetMapping("")
        public List<BorneDto> getAllBornes() {

                return borneService.getAllBornes();
        }

        //Méthode Get #2
        @GetMapping("/{id}")
        @ResponseStatus(code= HttpStatus.OK)
        public BorneDto getBorneById(@PathVariable Long id) {

                return borneService.getBorneById(id);
        }

        //Méthode Post
        @PostMapping("")
        @ResponseStatus(code=HttpStatus.CREATED)
        public BorneDto saveBorne(@Valid @RequestBody BorneDto borneDto,
                               BindingResult result) {
                Borne savedBorne = borneService.saveBorne(borneDto);
                return borneMapper.toDto(savedBorne);
        }

        //Méthode Delete
        @DeleteMapping("/{id}")
        @ResponseStatus(code=HttpStatus.NO_CONTENT)
        public void deleteBorne(@PathVariable Long id) {
                borneService.deleteBorne(id);
        }

        @PutMapping("/{id}")
        public BorneDto updateBorne(@PathVariable Long id,
                                 @Valid @RequestBody BorneDto borneDto) {
                borneDto.setId(id);
                Borne updatedBorne = borneService.saveBorne(borneDto);
                return borneMapper.toDto(updatedBorne);
        }
        @GetMapping("/mes-bornes")
        public List<BorneDto> getBornesForCurrentUser(Authentication authentication) {
                if (authentication == null) {

                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Utilisateur non authentifié.");
                }
                String userEmail = authentication.getName();
                return borneService.getBornesByUtilisateurEmail(userEmail);
        }

        @GetMapping("/search") // Un nouveau chemin pour la recherche
        public List<BorneDto> searchBornes(
                @RequestParam(required = false) String ville,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
                @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
                // Construisez votre objet BorneSearchCriteriaDto à partir des paramètres
                BorneSearchCriteriaDto criteria = new BorneSearchCriteriaDto();
                criteria.setVille(ville);
                criteria.setFromDate(fromDate);
                criteria.setToDate(toDate);
                criteria.setStartTime(startTime);
                criteria.setEndTime(endTime);

                return borneService.searchBornes(criteria); // Vous devrez créer cette méthode dans BorneService
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
