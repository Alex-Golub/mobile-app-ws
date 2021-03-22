package edu.mrdrprof.app.ws.repository;

import edu.mrdrprof.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Data access layer to handle DB
 * connections and queries
 *
 * @author Mr.Dr.Professor
 * @since 20/03/2021 15:31
 */
@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
  UserEntity findUserEntityByEmail(String email);
  UserEntity findUserEntityByUserId(String userId);
}
