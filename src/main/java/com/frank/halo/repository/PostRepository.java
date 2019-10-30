package com.frank.halo.repository;

import com.frank.halo.model.entity.Post;
import com.frank.halo.repository.base.BasePostRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;


/**
 * Post repository.
 *
 * @author johnniang
 * @author ryanwang
 */
public interface PostRepository extends BasePostRepository<Post>, JpaSpecificationExecutor<Post> {

    @Override
    @Query("select sum(p.visits) from Post p")
    Long countVisit();

    @Override
    @Query("select sum(p.likes) from Post p")
    Long countLike();

}
