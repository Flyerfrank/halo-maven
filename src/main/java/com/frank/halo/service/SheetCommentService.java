package com.frank.halo.service;

import com.frank.halo.model.entity.SheetComment;
import com.frank.halo.model.vo.SheetCommentWithSheetVO;
import com.frank.halo.service.base.BaseCommentService;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Sheet comment service interface.
 *
 * @author johnniang
 * @date 2019-04-24
 */
public interface SheetCommentService extends BaseCommentService<SheetComment> {

    @NonNull
    List<SheetCommentWithSheetVO> convertToWithPostVo(@Nullable List<SheetComment> sheetComments);

    @NonNull
    Page<SheetCommentWithSheetVO> convertToWithPostVo(@NonNull Page<SheetComment> sheetCommentPage);
}
