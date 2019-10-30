package com.frank.halo.repository;


import com.frank.halo.model.entity.Journal;
import com.frank.halo.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Journal repository.
 *
 * @author johnniang
 * @date 3/22/19
 */
public interface JournalRepository extends BaseRepository<Journal, Integer>, JpaSpecificationExecutor<Journal> {

}
