package com.frank.halo.model.params;

import com.frank.halo.model.dto.base.InputConverter;
import com.frank.halo.model.entity.Attachment;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Attachment params.
 *
 * @author ryanwang
 * @date : 2019/04/20
 */
@Data
public class AttachmentParam implements InputConverter<Attachment> {

    @NotBlank(message = "附件名称不能为空")
    @Size(max = 255, message = "附件名称的字符长度不能超过 {max}")
    private String name;

}
