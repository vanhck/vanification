package de.vanhck.data.dao;

import de.vanhck.data.DrivingKeyValue;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Lotti on 6/24/2017.
 */
@Transactional
public interface DrivingKeyValueDAO extends CrudRepository<DrivingKeyValue, Long> {
}
