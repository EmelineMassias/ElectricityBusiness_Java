package com.example.BC_Revision.controller.rest;

import com.example.BC_Revision.dto.BorneDto;
import com.example.BC_Revision.mapper.BorneMapper;
import com.example.BC_Revision.model.Borne;
import com.example.BC_Revision.service.BorneService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/bornes")
public class BorneControllerRest {

        private BorneService borneService;
        public BorneControllerRest(BorneService borneService){
                this.borneService = borneService;
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
        public Borne saveBorne(@Valid @RequestBody BorneDto borneDto,
                               BindingResult result) {
            return borneService.saveBorne(borneDto);
        }

        //Méthode Delete
        @DeleteMapping("/{id}")
        @ResponseStatus(code=HttpStatus.NO_CONTENT)
        public void deleteBorne(@PathVariable Long id) {
                borneService.deleteBorne(id);
        }

        @PutMapping("/{id}")
        public Borne updateBorne(@PathVariable Long id,
                                 @Valid @RequestBody BorneDto borneDto) {
                borneDto.setId(id);
                return borneService.saveBorne(borneDto);
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
