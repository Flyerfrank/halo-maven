package com.frank.halo.service;

import com.frank.halo.model.dto.TagDTO;
import com.frank.halo.model.entity.Tag;
import com.frank.halo.service.base.CrudService;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Tag service interface.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-03-14
 */
public interface TagService extends CrudService<Tag, Integer> {

    /**
     * Get tag by slug name
     *
     * @param slugName slug name
     * @return Tag
     */
    @NonNull
    Tag getBySlugNameOfNonNull(@NonNull String slugName);

    /**
     * Get tag by slug name
     *
     * @param slugName slug name
     * @return tag
     */
    @NonNull
    Tag getBySlugName(@NonNull String slugName);

    /**
     * Get tag by tag name.
     *
     * @param name name
     * @return Tag
     */
    @Nullable
    Tag getByName(@NonNull String name);

    /**
     * Converts to tag dto.
     *
     * @param tag tag must not be null
     * @return tag dto
     */
    @NonNull
    TagDTO convertTo(@NonNull Tag tag);

    /**
     * Converts to tag dtos.
     *
     * @param tags tag list
     * @return a list of tag output dto
     */
    @NonNull
    List<TagDTO> convertTo(@Nullable List<Tag> tags);
}
