package de.vanhck.data;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by renx on 24.06.17.
 */
@Transactional
public interface OptionDAO extends CrudRepository<Option,Long> {
}
