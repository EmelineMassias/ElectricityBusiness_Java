package com.example.BC_Revision.service;

import com.example.BC_Revision.dto.MediaDto;
import com.example.BC_Revision.model.Media;

import java.util.List;

public interface MediaService {

    Media saveMedia(MediaDto mediaDto);

    List<MediaDto> getAllMedias();

    MediaDto getMediaById(Long id);

    void deleteMedia(Long id);

}
