package edu.mrdrprof.app.ws.repository;

import edu.mrdrprof.app.ws.io.entity.AuthorityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Golub
 * @since 06-Apr-21 3:33 PM
 */
@Repository
public interface AuthorityRepository
        extends CrudRepository<AuthorityEntity, Long> {
  AuthorityEntity findByName(String name);
}
