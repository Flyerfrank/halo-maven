package com.frank.halo.controller.admin.api;

import com.frank.halo.service.TraceService;
import io.swagger.annotations.ApiOperation;
import org.springframework.boot.actuate.trace.http.HttpTrace;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Trace controller.
 *
 * @author johnniang
 * @date 19-6-18
 */
@RestController
@RequestMapping("/api/admin/traces")
public class TraceController {

    private final TraceService traceService;

    public TraceController(TraceService traceService) {
        this.traceService = traceService;
    }

    @GetMapping
    @ApiOperation("Lists http traces")
    public List<HttpTrace> listHttpTraces() {
        return traceService.listHttpTraces();
    }

}