package com.frank.halo.model.freemarker.tag;

import com.frank.halo.model.support.HaloConst;
import com.frank.halo.service.PostCommentService;
import freemarker.core.Environment;
import freemarker.template.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * Freemarker custom tag of comment.
 *
 * @author ryanwang
 * @date : 2019/3/22
 */
@Component
public class CommentTagDirective implements TemplateDirectiveModel {

    private final PostCommentService postCommentService;

    public CommentTagDirective(Configuration configuration, PostCommentService postCommentService) {
        this.postCommentService = postCommentService;
        configuration.setSharedVariable("commentTag", this);
    }

    @Override
    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        final DefaultObjectWrapperBuilder builder = new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_25);

        if (params.containsKey(HaloConst.METHOD_KEY)) {
            String method = params.get(HaloConst.METHOD_KEY).toString();
            switch (method) {
                case "latest":
                    int top = Integer.parseInt(params.get("top").toString());
                    env.setVariable("comments", builder.build().wrap(postCommentService.pageLatest(top)));
                    break;
                case "count":
                    env.setVariable("count", builder.build().wrap(postCommentService.count()));
                    break;
                default:
                    break;
            }
        }
        body.render(env.getOut());
    }
}
