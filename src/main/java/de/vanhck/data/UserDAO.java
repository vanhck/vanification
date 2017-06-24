package de.vanhck.data;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by Lotti on 6/24/2017.
 */
public interface UserDAO extends CrudRepository<User, Long> {
    public User findByName(String name);
}
