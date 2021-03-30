package edu.mrdrprof.app.ws.io.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Persisted into a table called users
 *
 * @author Mr.Dr.Professor
 * @since 20/03/2021 15:06
 */
@Entity(name = "users")
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
  @OneToMany(mappedBy = "userDetails", cascade = CascadeType.ALL)
  private List<AddressEntity> addresses;
}
