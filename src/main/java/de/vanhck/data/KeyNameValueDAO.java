package de.vanhck.data;

import de.vanhck.data.KeyNameValue;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import javax.transaction.Transactional;
import java.security.Key;
import java.util.List;

/**
 * Created by Lotti on 6/24/2017.
 */
@Transactional
public interface KeyNameValueDAO extends CrudRepository<KeyNameValue,Long> {
    KeyNameValue findByKeyName(String keyName);
}
