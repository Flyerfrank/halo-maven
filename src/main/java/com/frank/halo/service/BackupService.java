package com.frank.halo.service;

import com.frank.halo.model.dto.post.BasePostDetailDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Backup service interface.
 *
 * @author johnniang
 * @date 2019-04-26
 */
public interface BackupService {

    /**
     * Backup posts and sheets
     *
     * @param file file
     * @return post info
     */
    BasePostDetailDTO importMarkdowns(MultipartFile file) throws IOException;
}
