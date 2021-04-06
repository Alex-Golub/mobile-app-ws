package edu.mrdrprof.app.ws.io.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author Alex Golub
 * @since 06-Apr-21 3:07 PM
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleEntity implements Serializable {
  private static final long serialVersionUID = -6942029645643454347L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 20)
  private String name;

  @ManyToMany(mappedBy = "roles") // mapped by roles field from UserEntity class
  private Collection<UserEntity> users;

  @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
  @JoinTable(
          name = "roles_authorities",
          joinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"),
          inverseJoinColumns = @JoinColumn(name = "authorities_id", referencedColumnName = "id")
  )
  private Collection<AuthorityEntity> authorities;
}