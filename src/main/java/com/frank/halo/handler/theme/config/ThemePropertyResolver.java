package com.frank.halo.handler.theme.config;

import com.frank.halo.handler.theme.config.support.ThemeProperty;
import org.springframework.lang.NonNull;

import java.io.IOException;

/**
 * Theme file resolver.
 *
 * @author johnniang
 * @date 4/11/19
 */
public interface ThemePropertyResolver {

    /**
     * Resolves the theme file.
     *
     * @param content file content must not be null
     * @return theme file
     */
    @NonNull
    ThemeProperty resolve(@NonNull String content) throws IOException;
}
