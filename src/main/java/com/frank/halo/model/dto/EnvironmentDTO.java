package com.frank.halo.model.dto;

import com.frank.halo.model.enums.Mode;
import lombok.Data;

/**
 * Theme controller.
 *
 * @author ryanwang
 * @date : 2019/5/4
 */
@Data
public class EnvironmentDTO {

    private String database;

    private long startTime;

    private String version;

    private Mode mode;
}
