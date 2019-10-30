package com.frank.halo.model.dto;

import com.frank.halo.model.dto.base.OutputConverter;
import com.frank.halo.model.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Menu output dto.
 *
 * @author johnniang
 * @date 4/3/19
 */
@Data
@EqualsAndHashCode
@ToString
public class MenuDTO implements OutputConverter<MenuDTO, Menu> {

    private Integer id;

    private String name;

    private String url;

    private Integer priority;

    private String target;

    private String icon;

    private Integer parentId;
}
