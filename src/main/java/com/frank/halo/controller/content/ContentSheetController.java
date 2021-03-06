package com.frank.halo.controller.content;

import com.frank.halo.model.entity.Sheet;
import com.frank.halo.model.enums.PostStatus;
import com.frank.halo.model.support.HaloConst;
import com.frank.halo.service.SheetService;
import com.frank.halo.service.ThemeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Content sheet controller.
 *
 * @author ryanwang
 * @date : 2019-03-21
 */
@Controller
public class ContentSheetController {


    private final SheetService sheetService;

    private final ThemeService themeService;

    public ContentSheetController(SheetService sheetService,
                                  ThemeService themeService) {
        this.sheetService = sheetService;
        this.themeService = themeService;
    }

    /**
     * Render photo page
     *
     * @return template path: themes/{theme}/photos.ftl
     */
    @GetMapping(value = "/photos")
    public String photos() {
        return themeService.render("photos");
    }

    /**
     * Render links page
     *
     * @return template path: themes/{theme}/links.ftl
     */
    @GetMapping(value = "/links")
    public String links() {
        return themeService.render("links");
    }

    /**
     * Render custom sheet
     *
     * @param url   sheet url
     * @param model model
     * @return template path: themes/{theme}/sheet.ftl
     */
    @GetMapping(value = "/s/{url}")
    public String sheet(@PathVariable(value = "url") String url,
                        Model model) {
        Sheet sheet = sheetService.getBy(PostStatus.PUBLISHED, url);

        // sheet and post all can use
        model.addAttribute("sheet", sheetService.convertToDetail(sheet));
        model.addAttribute("post", sheetService.convertToDetail(sheet));
        model.addAttribute("is_sheet", true);

        if (themeService.templateExists(ThemeService.CUSTOM_SHEET_PREFIX + sheet.getTemplate() + HaloConst.SUFFIX_FTL)) {
            return themeService.render(ThemeService.CUSTOM_SHEET_PREFIX + sheet.getTemplate());
        }
        return themeService.render("sheet");
    }
}
