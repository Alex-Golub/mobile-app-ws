package edu.mrdrprof.app.ws.io.entity;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Persisted into a table called users
 *
 * @author Mr.Dr.Professor
 * @since 20/03/2021 15:06
 */
@Entity
@Table(name = "users") // remove name from @Entity to allow using JPQL
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserEntity implements Serializable {
  private static final long serialVersionUID = 3484347551592920965L;

  @Id // primary-key, auto-incremented
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false, length = 50)
  private String firstName;

  @Column(nullable = false, length = 50)
  private String lastName;

  @Column(nullable = false, length = 130/*, unique = true*/)
  // no duplicates with same email address
  private String email;

  @Column(nullable = false)
  private String encryptedPassword;

  private String emailVerificationToken;

  @Column(nullable = false)
  private boolean emailVerificationStatus = false;

  /**
   * one user can have many addresses.
   * This column is mapped-by AddressEntity userDetails field.
   */
  @OneToMany(/*fetch = FetchType.EAGER,*/ mappedBy = "userDetails", cascade = CascadeType.ALL)
  @LazyCollection(LazyCollectionOption.FALSE)
  private List<AddressEntity> addresses;

  @ManyToMany(/*fetch = FetchType.EAGER,*/       // once user read from db set roles right away
              cascade = CascadeType.PERSIST) // dont cascade all operation when userEntity deleted
  @LazyCollection(LazyCollectionOption.FALSE)
  @JoinTable(
          name = "users_roles",
          joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id")
  )
  private Collection<RoleEntity> roles;
}
