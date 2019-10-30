package com.frank.halo.service.impl;

import com.frank.halo.exception.BadRequestException;
import com.frank.halo.exception.NotFoundException;
import com.frank.halo.model.dto.post.BasePostMinimalDTO;
import com.frank.halo.model.entity.Post;
import com.frank.halo.model.entity.PostComment;
import com.frank.halo.model.params.CommentQuery;
import com.frank.halo.model.vo.PostCommentWithPostVO;
import com.frank.halo.repository.PostCommentRepository;
import com.frank.halo.repository.PostRepository;
import com.frank.halo.service.OptionService;
import com.frank.halo.service.PostCommentService;
import com.frank.halo.service.UserService;
import com.frank.halo.utils.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * PostCommentService implementation class
 *
 * @author ryanwang
 * @author johnniang
 * @date 2019-03-14
 */
@Slf4j
@Service
public class PostCommentServiceImpl extends BaseCommentServiceImpl<PostComment> implements PostCommentService {

    private final PostCommentRepository postCommentRepository;

    private final PostRepository postRepository;

    public PostCommentServiceImpl(PostCommentRepository postCommentRepository,
                                  PostRepository postRepository,
                                  UserService userService,
                                  OptionService optionService,
                                  ApplicationEventPublisher eventPublisher) {
        super(postCommentRepository, optionService, userService, eventPublisher);
        this.postCommentRepository = postCommentRepository;
        this.postRepository = postRepository;
    }

    @Override
    public Page<PostCommentWithPostVO> convertToWithPostVo(Page<PostComment> commentPage) {
        Assert.notNull(commentPage, "PostComment page must not be null");

        return new PageImpl<>(convertToWithPostVo(commentPage.getContent()), commentPage.getPageable(), commentPage.getTotalElements());

    }

    @Override
    public List<PostCommentWithPostVO> convertToWithPostVo(List<PostComment> postComments) {
        if (CollectionUtils.isEmpty(postComments)) {
            return Collections.emptyList();
        }

        // Fetch goods ids
        Set<Integer> postIds = ServiceUtils.fetchProperty(postComments, PostComment::getPostId);

        // Get all posts
        Map<Integer, Post> postMap = ServiceUtils.convertToMap(postRepository.findAllById(postIds), Post::getId);

        return postComments.stream()
                .filter(comment -> postMap.containsKey(comment.getPostId()))
                .map(comment -> {
                    // Convert to vo
                    PostCommentWithPostVO postCommentWithPostVO = new PostCommentWithPostVO().convertFrom(comment);

                    // Get post and set to the vo
                    postCommentWithPostVO.setPost(new BasePostMinimalDTO().convertFrom(postMap.get(comment.getPostId())));

                    return postCommentWithPostVO;
                }).collect(Collectors.toList());
    }

    @Override
    public Page<PostCommentWithPostVO> pageTreeBy(CommentQuery commentQuery, Pageable pageable) {
        Page<PostComment> postCommentPage = pageBy(commentQuery, pageable);

        return null;
    }

    @Override
    public void validateTarget(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException("该文章不存在或已删除").setErrorData(postId));

        if (post.getDisallowComment()) {
            throw new BadRequestException("该文章已经被禁止评论").setErrorData(postId);
        }
    }
}
