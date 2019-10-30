package com.frank.halo.service;

import com.frank.halo.model.dto.JournalDTO;
import com.frank.halo.model.dto.JournalWithCmtCountDTO;
import com.frank.halo.model.entity.Journal;
import com.frank.halo.model.params.JournalParam;
import com.frank.halo.model.params.JournalQuery;
import com.frank.halo.service.base.CrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * Journal service interface.
 *
 * @author johnniang
 * @author ryanwang
 * @date 2019-04-24
 */
public interface JournalService extends CrudService<Journal, Integer> {

    /**
     * Creates a journal.
     *
     * @param journalParam journal param must not be null
     * @return created journal
     */
    @NonNull
    Journal createBy(@NonNull JournalParam journalParam);

    /**
     * Gets latest journals.
     *
     * @param top max size
     * @return latest journal page
     */
    Page<Journal> pageLatest(int top);

    /**
     * Pages journals.
     *
     * @param journalQuery journal query must not be null
     * @param pageable     page info must not be null
     * @return a page of journal
     */
    @NonNull
    Page<Journal> pageBy(@NonNull JournalQuery journalQuery, @NonNull Pageable pageable);

    /**
     * Converts to journal dto.
     *
     * @param journal journal must not be null
     * @return journal dto
     */
    @NonNull
    JournalDTO convertTo(@NonNull Journal journal);

    /**
     * Converts to journal with comment count dto list.
     *
     * @param journals journal list
     * @return journal with comment count dto list
     */
    @NonNull
    List<JournalWithCmtCountDTO> convertToCmtCountDto(@Nullable List<Journal> journals);

    /**
     * Converts to journal with comment count dto page.
     *
     * @param journalPage journal page must not be null
     * @return a page of journal with comment count dto
     */
    @NonNull
    Page<JournalWithCmtCountDTO> convertToCmtCountDto(@NonNull Page<Journal> journalPage);
}
