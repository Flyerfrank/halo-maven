package com.frank.halo.repository;

import com.frank.halo.model.entity.PostComment;
import com.frank.halo.model.projection.CommentChildrenCountProjection;
import com.frank.halo.model.projection.CommentCountProjection;
import com.frank.halo.repository.base.BaseCommentRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * PostComment repository.
 *
 * @author johnniang
 * @date 3/21/19
 */
public interface PostCommentRepository extends BaseCommentRepository<PostComment> {

    @Query("select new com.frank.halo.model.projection.CommentCountProjection(count(comment.id), comment.postId) " +
            "from PostComment comment " +
            "where comment.postId in ?1 group by comment.postId")
    @NonNull
    @Override
    List<CommentCountProjection> countByPostIds(@NonNull Iterable<Integer> postIds);

    @Query("select new com.frank.halo.model.projection.CommentChildrenCountProjection(count(comment.id), comment.parentId) " +
            "from PostComment comment " +
            "where comment.parentId in ?1 " +
            "group by comment.parentId")
    @NonNull
    List<CommentChildrenCountProjection> findDirectChildrenCount(@NonNull Iterable<Long> commentIds);
}
