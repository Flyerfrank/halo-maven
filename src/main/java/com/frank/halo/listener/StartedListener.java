package com.frank.halo.listener;

import com.frank.halo.config.properties.HaloProperties;
import com.frank.halo.model.properties.PrimaryProperties;
import com.frank.halo.service.OptionService;
import com.frank.halo.service.ThemeService;
import com.frank.halo.service.UserService;
import com.frank.halo.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.util.ResourceUtils;

import java.net.URI;
import java.nio.file.*;
import java.util.Collections;

/**
 * The method executed after the application is started.
 *
 * @author ryanwang
 * @date : 2018/12/5
 */
@Slf4j
@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StartedListener implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private HaloProperties haloProperties;

    @Autowired
    private OptionService optionService;

    @Autowired
    private ThemeService themeService;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        // save halo version to database
        this.printStartInfo();
        this.initThemes();
    }

    private void printStartInfo() {
        String blogUrl = optionService.getBlogBaseUrl();

        log.info("Halo started at         {}", blogUrl);
        log.info("Halo admin started at   {}/admin", blogUrl);
        if (!haloProperties.isDocDisabled()) {
            log.debug("Halo doc was enable at  {}/swagger-ui.html", blogUrl);
        }
    }

    /**
     * Init internal themes
     */
    private void initThemes() {
        // Whether the blog has initialized
        Boolean isInstalled = optionService.getByPropertyOrDefault(PrimaryProperties.IS_INSTALLED, Boolean.class, false);

        if (haloProperties.isProductionEnv() && isInstalled) {
            // Skip
            return;
        }

        try {
            String themeClassPath = ResourceUtils.CLASSPATH_URL_PREFIX + ThemeService.THEME_FOLDER;

            URI themeUri = ResourceUtils.getURL(themeClassPath).toURI();

            log.debug("Theme uri: [{}]", themeUri);

            Path source;

            if (themeUri.getScheme().equalsIgnoreCase("jar")) {
                // Create new file system for jar
                FileSystem fileSystem = FileSystems.newFileSystem(themeUri, Collections.emptyMap());
                source = fileSystem.getPath("/BOOT-INF/classes/" + ThemeService.THEME_FOLDER);
            } else {
                source = Paths.get(themeUri);
            }

            // Create theme folder
            Path themePath = themeService.getBasePath();

            if (!haloProperties.isProductionEnv() || Files.notExists(themePath)) {
                FileUtils.copyFolder(source, themePath);
                log.info("Copied theme folder from [{}] to [{}]", source, themePath);
            } else {
                log.info("Skipped copying theme folder due to existence of theme folder");
            }
        } catch (Exception e) {
            throw new RuntimeException("Initialize internal theme to user path error", e);
        }
    }

}
