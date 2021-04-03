package edu.mrdrprof.app.ws.repository;

import edu.mrdrprof.app.ws.io.entity.AddressEntity;
import edu.mrdrprof.app.ws.io.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Mr.Dr.Professor
 * @since 27-Mar-21 2:33 PM
 */
@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
  List<AddressEntity> findAllByUserDetails(UserEntity user);
  AddressEntity findByAddressId(String addressId);
}
