package com.frank.halo.security.authentication;

import com.frank.halo.security.support.UserDetail;
import org.springframework.lang.NonNull;

/**
 * Authentication.
 *
 * @author johnniang
 */
public interface Authentication {

    /**
     * Get user detail.
     *
     * @return user detail
     */
    @NonNull
    UserDetail getDetail();
}
