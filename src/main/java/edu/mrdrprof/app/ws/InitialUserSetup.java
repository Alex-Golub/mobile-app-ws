package edu.mrdrprof.app.ws;

import edu.mrdrprof.app.ws.io.entity.AuthorityEntity;
import edu.mrdrprof.app.ws.io.entity.RoleEntity;
import edu.mrdrprof.app.ws.io.entity.UserEntity;
import edu.mrdrprof.app.ws.repository.AuthorityRepository;
import edu.mrdrprof.app.ws.repository.RoleRepository;
import edu.mrdrprof.app.ws.repository.UserRepository;
import edu.mrdrprof.app.ws.shared.Utils;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Create single Admin user with read, write and delete
 * authorities.
 * Methods with @EventListener will be triggered once
 * Spring broadcast ApplicationReadyEvent
 *
 * @author Alex Golub
 * @since 06-Apr-21 4:02 PM
 */
@Component
@AllArgsConstructor
@Transactional
// changes are committed to db only if all repository operations complete successfully
public class InitialUserSetup {
  private final AuthorityRepository authorityRepository;
  private final RoleRepository roleRepository;
  private final Utils utils;
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  private final UserRepository userRepository;

  @EventListener
  public void onApplicationEvent(ApplicationReadyEvent event) {
    System.out.println(">> ApplicationReadyEvent triggered");

    AuthorityEntity read = getAuthorityEntity("READ_AUTHORITY");
    AuthorityEntity write = getAuthorityEntity("WRITE_AUTHORITY");
    AuthorityEntity delete = getAuthorityEntity("DELETE_AUTHORITY");

    RoleEntity roleUser = getRole("ROLE_USER", Arrays.asList(read, write));
    RoleEntity roleAdmin = getRole("ROLE_ADMIN", Arrays.asList(read, write, delete));

    if (roleAdmin == null) {
      return;
    }

    userRepository.save(createAdmin(roleAdmin));
  }

  private UserEntity createAdmin(RoleEntity admin) {
    final int PASS_ID_LENGTH = 30;
    return UserEntity.builder()
            .firstName("Mr.")
            .lastName("Dr.Admin")
            .email("admin@admin.com")
            .emailVerificationStatus(true)
            .userId(utils.generateRandomString(PASS_ID_LENGTH))
            .encryptedPassword(bCryptPasswordEncoder.encode("4321"))
            .roles(List.of(admin))
            .build();
  }

  private AuthorityEntity getAuthorityEntity(String name) {
    // first check if such authority exists in authorityRepository
    AuthorityEntity authorityEntity = authorityRepository.findByName(name);
    if (authorityEntity == null) {
      authorityEntity = AuthorityEntity.builder().name(name).build();

      authorityRepository.save(authorityEntity); // operation changes db thus must be transactional
    }

    return authorityEntity;
  }

  private RoleEntity getRole(String name, Collection<AuthorityEntity> authorities) {
    // first check if such role exists in roleRepository
    RoleEntity roleEntity = roleRepository.findByName(name);
    if (roleEntity == null) {
      roleEntity = RoleEntity.builder()
              .name(name)
              .authorities(authorities)
              .build();

      roleRepository.save(roleEntity); // operation changes db thus must be transactional
    }

    return roleEntity;
  }
}
