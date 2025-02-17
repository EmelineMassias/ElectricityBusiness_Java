package com.example.BC_Revision.service.impl;

import com.example.BC_Revision.dto.MediaDto;
import com.example.BC_Revision.mapper.MediaMapper;
import com.example.BC_Revision.model.Borne;
import com.example.BC_Revision.model.Media;
import com.example.BC_Revision.repository.BorneRepository;
import com.example.BC_Revision.repository.MediaRepository;
import com.example.BC_Revision.repository.UtilisateurRepository;
import com.example.BC_Revision.service.MediaService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class MediaServiceImpl implements MediaService {

    private MediaMapper mediaMapper;
    private MediaDto mediaDto;
    private MediaRepository mediaRepository;
    private BorneRepository borneRepository;

    public MediaServiceImpl(MediaRepository mediaRepository, BorneRepository borneRepository,
                            UtilisateurRepository utilisateurRepository){
        this.mediaRepository = mediaRepository;
        this.borneRepository = borneRepository;

    }



    @Override
    public Media saveMedia(MediaDto mediaDto) {

        Media media = mediaMapper.toEntity(mediaDto);
        if(mediaDto.getBorneId() != null ){
            Borne borne = borneRepository
                    .findById(mediaDto
                            .getBorneId())
                            .orElse(null);
            media.setBorne(borne);
        }
        return mediaRepository.save(media);
    }

    @Override
    public List<MediaDto> getAllMedias() {
        List<Media> medias = mediaRepository.findAll();
        return medias.stream().map(mediaMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MediaDto getMediaById(Long id) {
        return mediaRepository
                .findById(id).map(mediaMapper::toDto)
                .orElse(null);

    }

    @Override
    public void deleteMedia(Long id) {
        mediaRepository.deleteById(id);

    }
}
