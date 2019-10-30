package com.frank.halo.service.impl;

import cn.hutool.core.io.IoUtil;
import com.frank.halo.model.dto.post.BasePostDetailDTO;
import com.frank.halo.service.BackupService;
import com.frank.halo.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Backup service implementation.
 *
 * @author johnniang
 * @date 2019-04-26
 */
@Service
public class BackupServiceImpl implements BackupService {

    private final PostService postService;

    public BackupServiceImpl(PostService postService) {
        this.postService = postService;
    }

    @Override
    public BasePostDetailDTO importMarkdowns(MultipartFile file) throws IOException {

        // Read markdown content.
        String markdown = IoUtil.read(file.getInputStream(), StandardCharsets.UTF_8);

        // TODO sheet import

        return postService.importMarkdown(markdown, file.getOriginalFilename());
    }
}
