package edu.mrdrprof.app.ws.io.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;

/**
 * @author Alex Golub
 * @since 06-Apr-21 3:15 PM
 */
@Entity
@Table(name = "authorities")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthorityEntity implements Serializable {
  private static final long serialVersionUID = -6824743375201714785L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 30)
  private String name;

  @ManyToMany(mappedBy = "authorities")
  private Collection<RoleEntity> roles;
}
