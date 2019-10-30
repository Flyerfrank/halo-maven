package com.frank.halo.controller.admin.api;

import com.frank.halo.cache.lock.CacheLock;
import com.frank.halo.model.dto.EnvironmentDTO;
import com.frank.halo.model.dto.StatisticDTO;
import com.frank.halo.model.params.LoginParam;
import com.frank.halo.model.support.BaseResponse;
import com.frank.halo.security.token.AuthToken;
import com.frank.halo.service.AdminService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Admin controller.
 *
 * @author johnniang
 * @author ryanwang
 * @date 3/19/19
 */
@Slf4j
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Get some statistics about the count of posts, the count of comments, etc.
     *
     * @return counts
     */
    @GetMapping("counts")
    @ApiOperation("Gets count info")
    public StatisticDTO getCount() {
        return adminService.getCount();
    }

    @GetMapping("environments")
    @ApiOperation("Gets environments info")
    public EnvironmentDTO getEnvironments() {
        return adminService.getEnvironments();
    }

    @PostMapping("login")
    @ApiOperation("Login")
    @CacheLock(autoDelete = false)
    public AuthToken auth(@RequestBody @Valid LoginParam loginParam) {
        return adminService.authenticate(loginParam);
    }

    @PostMapping("logout")
    @ApiOperation("Logs out (Clear session)")
    @CacheLock(autoDelete = false)
    public void logout() {
        adminService.clearToken();
    }

    @PostMapping("refresh/{refreshToken}")
    @ApiOperation("Refreshes token")
    @CacheLock(autoDelete = false)
    public AuthToken refresh(@PathVariable("refreshToken") String refreshToken) {
        return adminService.refreshToken(refreshToken);
    }

    @PutMapping("halo-admin")
    @ApiOperation("Updates halo-admin manually")
    public void updateAdmin() {
        adminService.updateAdminAssets();
    }

    @GetMapping("spring/logs")
    @ApiOperation("Get application logs")
    public BaseResponse<String> getSpringLogs() {
        return BaseResponse.ok(HttpStatus.OK.getReasonPhrase(),adminService.getSpringLogs());
    }
}
