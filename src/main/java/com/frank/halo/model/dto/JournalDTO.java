package com.frank.halo.model.dto;

import com.frank.halo.model.dto.base.OutputConverter;
import com.frank.halo.model.entity.Journal;
import lombok.Data;

import java.util.Date;

/**
 * Journal dto.
 *
 * @author johnniang
 * @date 19-4-24
 */
@Data
public class JournalDTO implements OutputConverter<JournalDTO, Journal> {

    private Integer id;

    private String content;

    private Long likes;

    private Date createTime;
}
