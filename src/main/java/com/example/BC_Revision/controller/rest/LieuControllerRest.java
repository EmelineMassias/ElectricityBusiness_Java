package com.example.BC_Revision.controller.rest;

import com.example.BC_Revision.dto.LieuDto;
import com.example.BC_Revision.mapper.LieuMapper;
import com.example.BC_Revision.model.Lieu;
import com.example.BC_Revision.service.LieuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/lieux")
public class LieuControllerRest {
    private LieuService lieuService;
    public LieuControllerRest(LieuService lieuService){
        this.lieuService = lieuService;
    }

    //Méthode Get #1
    @GetMapping("")
    public List<LieuDto> getAllLieux() {

        return lieuService.getAllLieux();
    }

    //Méthode Get #2
    @GetMapping("/{id}")
    @ResponseStatus(code= HttpStatus.OK)
    public LieuDto getLieuById(@PathVariable Long id) {

        return lieuService.getLieuById(id);
    }

    //Méthode Post
    @PostMapping("")
    @ResponseStatus(code=HttpStatus.CREATED)
    public Lieu saveLieu(@Valid @RequestBody LieuDto lieuDto,
                         BindingResult result) {
        return lieuService.saveLieu(lieuDto);
    }

    //Méthode Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteLieu(@PathVariable Long id) {
        lieuService.deleteLieu(id);
    }

//

    @PutMapping("/{id}")
    public Lieu updateLieu(@PathVariable Long id,
                           @Valid @RequestBody LieuDto lieuDto) {
        lieuDto.setId(id);
        return lieuService.saveLieu(lieuDto);
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
