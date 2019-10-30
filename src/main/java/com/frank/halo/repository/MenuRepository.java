package com.frank.halo.repository;

import com.frank.halo.model.entity.Menu;
import com.frank.halo.repository.base.BaseRepository;
import org.springframework.lang.NonNull;

/**
 * Menu repository.
 *
 * @author johnniang
 */
public interface MenuRepository extends BaseRepository<Menu, Integer> {

    boolean existsByName(@NonNull String name);

    boolean existsByIdNotAndName(@NonNull Integer id, @NonNull String name);
}
