package com.frank.halo.controller.content.api;

import com.frank.halo.model.dto.MenuDTO;
import com.frank.halo.model.vo.MenuVO;
import com.frank.halo.service.MenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * Portal menu controller.
 *
 * @author johnniang
 * @author ryanwang
 * @date 4/3/19
 */
@RestController("ApiContentMenuController")
@RequestMapping("/api/content/menus")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping
    @ApiOperation("Lists all menus")
    public List<MenuDTO> listAll(@SortDefault(sort = "priority", direction = DESC) Sort sort) {
        return menuService.listDtos(sort);
    }

    @GetMapping(value = "tree_view")
    @ApiOperation("Lists menus with tree view")
    public List<MenuVO> listMenusTree(@SortDefault(sort = "createTime", direction = DESC) Sort sort) {
        return menuService.listAsTree(sort);
    }
}
