package com.construction.service.mapper;

import static java.util.stream.Collectors.toSet;

import com.construction.domain.Photo;
import java.util.Set;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
public class PhotoMapperHelper {

    @Named("photoLinks")
    public Set<String> mapPhotos(Set<Photo> photos) {
        if (photos == null) return null;
        return photos.stream().map(Photo::getUrl).collect(toSet());
    }
}
