package de.vanhck.data;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

/**
 * Created by Lotti on 6/24/2017.
 */
@Transactional
public interface UserDAO extends CrudRepository<User, Long> {
    public User findByName(String name);
}
