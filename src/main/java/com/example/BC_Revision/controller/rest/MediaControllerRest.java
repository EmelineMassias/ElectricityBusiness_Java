package com.example.BC_Revision.controller.rest;

import com.example.BC_Revision.dto.MediaDto;
import com.example.BC_Revision.mapper.MediaMapper;
import com.example.BC_Revision.model.Media;
import com.example.BC_Revision.service.MediaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Validated
@RequestMapping("/medias")
public class MediaControllerRest {
    private MediaService mediaService;
    public MediaControllerRest(MediaService mediaService){
        this.mediaService = mediaService;
    }

    //Méthode Get #1
    @GetMapping("")
    public List<MediaDto> getAllMedias() {

        return mediaService.getAllMedias();
    }

    //Méthode Get #2
    @GetMapping("/{id}")
    @ResponseStatus(code= HttpStatus.OK)
    public MediaDto getMediaById(@PathVariable Long id) {
        return mediaService.getMediaById(id);
    }

    //Méthode Post
    @PostMapping("")
    @ResponseStatus(code=HttpStatus.CREATED)
    public Media saveMedia(@Valid @RequestBody MediaDto mediaDto,
                           BindingResult result) {
        return mediaService.saveMedia(mediaDto);
    }

    //Méthode Delete
    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteMedia(@PathVariable Long id) {
        mediaService.deleteMedia(id);
    }

//

    @PutMapping("/{id}")
    public Media updateMedia(@PathVariable Long id,
                             @Valid @RequestBody MediaDto mediaDto) {
        mediaDto.setId(id);
        return mediaService.saveMedia(mediaDto);
    }
}
