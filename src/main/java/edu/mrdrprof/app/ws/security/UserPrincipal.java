package edu.mrdrprof.app.ws.security;

import edu.mrdrprof.app.ws.io.entity.AuthorityEntity;
import edu.mrdrprof.app.ws.io.entity.RoleEntity;
import edu.mrdrprof.app.ws.io.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Alex Golub
 * @since 06-Apr-21 6:56 PM
 */
public class UserPrincipal implements UserDetails {
  private static final long serialVersionUID = -2672354501180630076L;
  private final UserEntity userEntity;
  private final String userId; // public userId

  public UserPrincipal(UserEntity userEntity) {
    this.userEntity = userEntity;
    this.userId = userEntity.getUserId();
  }

  /**
   * Return a collection of all roles and authorities for this
   * authenticated user
   */
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    // users roles and authorities should be unique thus using Set collection
    Collection<GrantedAuthority> authorities = new HashSet<>();
    Collection<AuthorityEntity> authorityEntities = new HashSet<>();

    Collection<RoleEntity> userEntityRoles = userEntity.getRoles();
    if (userEntityRoles == null) {
      return authorities; // authenticated user has no roles
    }

    // extract all roles and authorities
    userEntityRoles.forEach(roleEntity -> {
      authorities.add((new SimpleGrantedAuthority(roleEntity.getName())));
      authorityEntities.addAll(roleEntity.getAuthorities());
    });

    authorityEntities.forEach(authorityEntity ->
                                      authorities.add(new SimpleGrantedAuthority(authorityEntity.getName()))
    );

    return authorities;
  }

  @Override
  public String getPassword() {
    return userEntity.getEncryptedPassword();
  }

  @Override
  public String getUsername() {
    return userEntity.getEmail(); // email is used to load user
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    // NB: email verification functionality is not implemented (Amazon $$$)
//    return userEntity.isEmailVerificationStatus();
    return true;
  }

  public String getUserId() {
    return userId;
  }
}
