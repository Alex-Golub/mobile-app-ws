package edu.mrdrprof.app.ws.io.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Mr.Dr.Professor
 * @since 26-Mar-21 7:55 PM
 */
@Entity(name = "addresses")
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddressEntity implements Serializable {
  private static final long serialVersionUID = -1950339052271451415L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 30)
  private String addressId; // safe to share publicly

  @Column(nullable = false, length = 30)
  private String city;

  @Column(nullable = false, length = 20)
  private String country;

  @Column(nullable = false, length = 100)
  private String streetName;

  @Column(nullable = false, length = 10)
  private String postalCode;

  @Column(nullable = false, length = 10)
  private String type;

  /**
   * many addresses can belong to one user.
   * This column binds to UserEntity primary-key.
   */
  @ManyToOne
  @JoinColumn(name = "users_id")
  private UserEntity userDetails;
}
