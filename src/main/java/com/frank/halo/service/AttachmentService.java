package com.frank.halo.service;

import com.frank.halo.model.dto.AttachmentDTO;
import com.frank.halo.model.entity.Attachment;
import com.frank.halo.model.params.AttachmentQuery;
import com.frank.halo.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * Attachment service.
 *
 * @author johnniang
 * @date : 2019-03-14
 */
public interface AttachmentService extends CrudService<Attachment, Integer> {

    /**
     * Pages attachment output dtos.
     *
     * @param pageable page info must not be null
     * @return a page of attachment output dto
     */
    @NonNull
    Page<AttachmentDTO> pageDtosBy(@NonNull Pageable pageable, AttachmentQuery attachmentQuery);

    /**
     * Uploads file.
     *
     * @param file multipart file must not be null
     * @return attachment info
     * @throws FileOperationException throws when failed to filehandler the file
     */
    @NonNull
    Attachment upload(@NonNull MultipartFile file);

    /**
     * Removes attachment permanently.
     *
     * @param id attachment id must not be null
     * @return attachment detail deleted
     */
    @NonNull
    Attachment removePermanently(@NonNull Integer id);

    /**
     * Converts to attachment output dto.
     *
     * @param attachment attachment must not be null
     * @return an attachment output dto
     */
    @NonNull
    AttachmentDTO convertToDto(@NonNull Attachment attachment);

    /**
     * List all media type.
     *
     * @return list of media type
     */
    List<String> listAllMediaType();
}
