package com.frank.halo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frank.halo.cache.InMemoryCacheStore;
import com.frank.halo.cache.StringCacheStore;
import com.frank.halo.config.properties.HaloProperties;
import com.frank.halo.filter.CorsFilter;
import com.frank.halo.filter.LogFilter;
import com.frank.halo.security.filter.AdminAuthenticationFilter;
import com.frank.halo.security.filter.ApiAuthenticationFilter;
import com.frank.halo.security.filter.ContentFilter;
import com.frank.halo.security.handler.ContentAuthenticationFailureHandler;
import com.frank.halo.security.handler.DefaultAuthenticationFailureHandler;
import com.frank.halo.service.OptionService;
import com.frank.halo.service.UserService;
import com.frank.halo.utils.HttpClientUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

/**
 * Halo configuration.
 *
 * @author johnniang
 */
@Configuration
@EnableConfigurationProperties(HaloProperties.class)
public class HaloConfiguration {

    private final static int TIMEOUT = 5000;

    @Bean
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        builder.failOnEmptyBeans(false);
        return builder.build();
    }

    @Bean
    public RestTemplate httpsRestTemplate(RestTemplateBuilder builder) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        RestTemplate httpsRestTemplate = builder.build();
        httpsRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(HttpClientUtils.createHttpsClient(TIMEOUT)));
        return httpsRestTemplate;
    }

    @Bean
    @ConditionalOnMissingBean
    public StringCacheStore stringCacheStore() {
        return new InMemoryCacheStore();
    }

    /**
     * Creates a CorsFilter.
     *
     * @return Cors filter registration bean
     */
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsFilter = new FilterRegistrationBean<>();

        corsFilter.setOrder(Ordered.HIGHEST_PRECEDENCE + 10);
        corsFilter.setFilter(new CorsFilter());
        corsFilter.addUrlPatterns("/api/*");

        return corsFilter;
    }

    /**
     * Creates a LogFilter.
     *
     * @return Log filter registration bean
     */
    @Bean
    public FilterRegistrationBean<LogFilter> logFilter() {
        FilterRegistrationBean<LogFilter> logFilter = new FilterRegistrationBean<>();

        logFilter.setOrder(Ordered.HIGHEST_PRECEDENCE + 9);
        logFilter.setFilter(new LogFilter());
        logFilter.addUrlPatterns("/*");

        return logFilter;
    }

    @Bean
    public FilterRegistrationBean<ContentFilter> contentFilter(HaloProperties haloProperties,
                                                               OptionService optionService) {
        ContentFilter contentFilter = new ContentFilter(haloProperties, optionService);
        contentFilter.setFailureHandler(new ContentAuthenticationFailureHandler());
        contentFilter.addExcludeUrlPatterns("/api/**", "/install", "/version", "/admin/**", "/js/**", "/css/**");

        FilterRegistrationBean<ContentFilter> contentFrb = new FilterRegistrationBean<>();
        contentFrb.addUrlPatterns("/*");
        contentFrb.setFilter(contentFilter);
        contentFrb.setOrder(-1);

        return contentFrb;
    }

    @Bean
    public FilterRegistrationBean<ApiAuthenticationFilter> apiAuthenticationFilter(HaloProperties haloProperties,
                                                                                   ObjectMapper objectMapper,
                                                                                   OptionService optionService) {
        ApiAuthenticationFilter apiFilter = new ApiAuthenticationFilter(haloProperties, optionService);
        apiFilter.addExcludeUrlPatterns(
                "/api/content/*/comments",
                "/api/content/**/comments/**",
                "/api/content/options/comment"
        );

        DefaultAuthenticationFailureHandler failureHandler = new DefaultAuthenticationFailureHandler();
        failureHandler.setProductionEnv(haloProperties.isProductionEnv());
        failureHandler.setObjectMapper(objectMapper);

        // Set failure handler
        apiFilter.setFailureHandler(failureHandler);

        FilterRegistrationBean<ApiAuthenticationFilter> authenticationFilter = new FilterRegistrationBean<>();
        authenticationFilter.setFilter(apiFilter);
        authenticationFilter.addUrlPatterns("/api/content/*");
        authenticationFilter.setOrder(0);

        return authenticationFilter;
    }

    @Bean
    public FilterRegistrationBean<AdminAuthenticationFilter> adminAuthenticationFilter(StringCacheStore cacheStore,
                                                                                       UserService userService,
                                                                                       HaloProperties haloProperties,
                                                                                       ObjectMapper objectMapper,
                                                                                       OptionService optionService) {
        AdminAuthenticationFilter adminAuthenticationFilter = new AdminAuthenticationFilter(cacheStore, userService, haloProperties, optionService);

        DefaultAuthenticationFailureHandler failureHandler = new DefaultAuthenticationFailureHandler();
        failureHandler.setProductionEnv(haloProperties.isProductionEnv());
        failureHandler.setObjectMapper(objectMapper);

        // Config the admin filter
        adminAuthenticationFilter.addExcludeUrlPatterns(
                "/api/admin/login",
                "/api/admin/refresh/*",
                "/api/admin/installations",
                "/api/admin/recoveries/migrations/*"
        );
        adminAuthenticationFilter.setFailureHandler(
                failureHandler);

        FilterRegistrationBean<AdminAuthenticationFilter> authenticationFilter = new FilterRegistrationBean<>();
        authenticationFilter.setFilter(adminAuthenticationFilter);
        authenticationFilter.addUrlPatterns("/api/admin/*", "/api/content/comments");
        authenticationFilter.setOrder(1);

        return authenticationFilter;
    }
}
