package com.rentalapp.services;

import com.rentalapp.models.Tag;
import com.rentalapp.repositories.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public List<Tag> getTagsByIds(List<Long> selectedTagIds) {
    return tagRepository.findAllById(selectedTagIds);
    }
}
