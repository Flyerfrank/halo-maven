package com.frank.halo.service;

import com.frank.halo.model.dto.LogDTO;
import com.frank.halo.model.entity.Log;
import com.frank.halo.service.base.CrudService;
import org.springframework.data.domain.Page;

/**
 * Log service interface.
 *
 * @author johnniang
 * @date 2019-03-14
 */
public interface LogService extends CrudService<Log, Long> {

    /**
     * Lists latest logs.
     *
     * @param top top number must not be less than 0
     * @return a page of latest logs
     */
    Page<LogDTO> pageLatest(int top);
}
