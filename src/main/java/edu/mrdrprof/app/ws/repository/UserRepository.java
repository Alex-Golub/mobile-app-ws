package edu.mrdrprof.app.ws.repository;

import edu.mrdrprof.app.ws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Data access layer to handle DB
 * connections and queries
 *
 * @author Mr.Dr.Professor
 * @since 20/03/2021 15:31
 */
@Repository
public interface UserRepository /*extends CrudRepository*/
        extends PagingAndSortingRepository<UserEntity, Long> {

  UserEntity findUserEntityByEmail(String email);

  UserEntity findUserEntityByUserId(String userId);

  // ============ Native SQL Query Platform dependent ============
  // hard coding value of email_verification_status - bad practice
  @Query(value = "SELECT * FROM users u WHERE u.email_verification_status = 'true'",
         countQuery = "SELECT count(*) FROM users u WHERE u.email_verification_status = 'true'",
         nativeQuery = true)
  Page<UserEntity> findAllUsersWithConfirmationEmailAddress(Pageable pageable);

  // Query with POSITIONAL parameters ?1 (=first parameter will be replaced with firstName)
  @Query(value = "SELECT * FROM users u WHERE LOWER(first_name) LIKE LOWER(?1)",
         nativeQuery = true)
  List<UserEntity> findUserByFirstName(String firstName);

  // Querying using NAMED parameters. :lastName dont have to be exact match
  // with lastName parameter but it have to be the same as the value in @Param.
  // Advantage over POSITIONAL parameters is that order of parameters is not important
  @Query(value = "SELECT * FROM users u WHERE UPPER(last_name) LIKE UPPER(:lastName)",
         nativeQuery = true)
  List<UserEntity> findUserByLastName(@Param(value = "lastName") String userLastName);

  // E.g. that order is not important
  @Query(value = "SELECT * FROM users u " +
                 "WHERE u.first_name = :first_name " +
                 "AND u.email = :email " +
                 "AND u.last_name = :last_name",
         nativeQuery = true)
  List<UserEntity> findUserByFirstNameEmailAndLastName(
          @Param("email") String email,
          @Param("last_name") String lastName,
          @Param("first_name") String firstName
  );

  // using LIKE (can also use newer REGEXP)
  @Query(value = "SELECT * FROM users u " +
                 "WHERE u.first_name LIKE %:keyword% " +
                 "OR u.last_name LIKE %:keyword%",
         nativeQuery = true)
  List<UserEntity> findUserByKeyword(@Param("keyword") String keyword);

  // Update query which modifies a record in DB.
  // Transactional make sure query committed successfully
  // otherwise DB should not be modified (i.e. rollback happens if error occurred)
  @Transactional
  @Modifying
  @Query(value = "UPDATE users u " +
                 "SET u.email_verification_status = :status " +
                 "WHERE user_id = :userId",
         nativeQuery = true)
  void updateUserEmailVerificationStatus(@Param("status") boolean status,
                                         @Param("userId") String userId);

  // ============ JPQL Platform independent ============
  // E.g. for JPQL query which is using the entity name rather then entity table
  // and fields from the entity class rather then column names
  @Query("SELECT user " +
         "FROM UserEntity user " +
         "WHERE user.firstName = :firstName AND " +
         "user.lastName = :lastName")
  UserEntity findUserEntityByFullName(@Param("firstName") String firstName,
                                      @Param("lastName") String lastName);

  // Return specified number of columns
  @Query("SELECT user.firstName, user.lastName " +
         "FROM UserEntity user " +
         "WHERE user.userId = :userId")
  List<Object[]> findUserEntityFullNameByUserId(@Param("userId") String userId);

  @Transactional
  @Modifying
  @Query("UPDATE UserEntity user " +
         "SET user.emailVerificationStatus = :status " +
         "WHERE user.userId = :userId")
  void updateUserEmailVerificationStatus2(@Param("status") boolean status,
                                          @Param("userId") String userId);
}