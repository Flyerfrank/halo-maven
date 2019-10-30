package com.frank.halo.service;

import com.frank.halo.model.entity.PostComment;
import com.frank.halo.model.params.CommentQuery;
import com.frank.halo.model.vo.PostCommentWithPostVO;
import com.frank.halo.service.base.BaseCommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Post comment service interface.
 *
 * @author johnniang
 * @date 2019-03-14
 */
public interface PostCommentService extends BaseCommentService<PostComment> {

    /**
     * Converts to with post vo.
     *
     * @param commentPage comment page must not be null
     * @return a page of comment with post vo
     */
    @NonNull
    Page<PostCommentWithPostVO> convertToWithPostVo(@NonNull Page<PostComment> commentPage);

    /**
     * Converts to with post vo
     *
     * @param postComments comment list
     * @return a list of comment with post vo
     */
    @NonNull
    List<PostCommentWithPostVO> convertToWithPostVo(@Nullable List<PostComment> postComments);

    @NonNull
    Page<PostCommentWithPostVO> pageTreeBy(@NonNull CommentQuery commentQuery, @NonNull Pageable pageable);
}
