package com.frank.halo.handler.theme.config.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.frank.halo.handler.theme.config.ThemePropertyResolver;
import com.frank.halo.handler.theme.config.support.ThemeProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;

/**
 * Yaml theme file resolver.
 *
 * @author johnniang
 * @date 4/11/19
 */
@Service
public class YamlThemePropertyResolver implements ThemePropertyResolver {

    private final ObjectMapper yamlMapper;

    public YamlThemePropertyResolver() {
        yamlMapper = new ObjectMapper(new YAMLFactory());
        yamlMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    @Override
    public ThemeProperty resolve(String content) throws IOException {
        Assert.hasText(content, "Theme file content must not be null");

        return yamlMapper.readValue(content, ThemeProperty.class);
    }
}
