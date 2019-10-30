package com.frank.halo.model.params;

import com.frank.halo.model.entity.JournalComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Journal comment param.
 *
 * @author johnniang
 * @date 3/22/19
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class JournalCommentParam extends BaseCommentParam<JournalComment> {

}
