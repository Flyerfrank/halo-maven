package com.frank.halo.controller.admin.api;

import com.frank.halo.cache.lock.CacheLock;
import com.frank.halo.exception.BadRequestException;
import com.frank.halo.model.properties.PrimaryProperties;
import com.frank.halo.service.OptionService;
import com.frank.halo.service.RecoveryService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * Recovery controller
 *
 * @author johnniang
 * @date 19-4-26
 */
@RestController
@RequestMapping("/api/admin/recoveries")
public class RecoveryController {

    private final RecoveryService recoveryService;

    private final OptionService optionService;

    public RecoveryController(RecoveryService recoveryService,
                              OptionService optionService) {
        this.recoveryService = recoveryService;
        this.optionService = optionService;
    }

    @PostMapping("migrations/v0_4_3")
    @ApiOperation("Migrates from halo v0.4.3")
    @CacheLock
    public void migrateFromVersion_0_4_3(
            @ApiParam("This file content type should be json")
            @RequestPart("file") MultipartFile file) {
        if (optionService.getByPropertyOrDefault(PrimaryProperties.IS_INSTALLED, Boolean.class, false)) {
            throw new BadRequestException("无法在博客初始化完成之后迁移数据");
        }

        recoveryService.migrateFromV0_4_3(file);
    }
}
