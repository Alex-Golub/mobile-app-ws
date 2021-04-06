package edu.mrdrprof.app.ws.repository;

import edu.mrdrprof.app.ws.io.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Alex Golub
 * @since 06-Apr-21 3:30 PM
 */
@Repository
public interface RoleRepository
        extends CrudRepository<RoleEntity, Long> {
  RoleEntity findByName(String name);
}
