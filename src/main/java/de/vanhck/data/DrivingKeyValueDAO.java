package de.vanhck.data;

import de.vanhck.data.DrivingKeyValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * Created by Lotti on 6/24/2017.
 */
@Transactional
public interface DrivingKeyValueDAO extends CrudRepository<DrivingKeyValue, Long> {
}
