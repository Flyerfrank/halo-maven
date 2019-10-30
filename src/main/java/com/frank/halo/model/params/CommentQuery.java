package com.frank.halo.model.params;

import com.frank.halo.model.enums.CommentStatus;
import lombok.Data;

;

/**
 * Comment query params.
 *
 * @author ryanwang
 * @date : 2019/04/18
 */
@Data
public class CommentQuery {

    /**
     * Keyword.
     */
    private String keyword;

    /**
     * Comment status.
     */
    private CommentStatus status;
}
