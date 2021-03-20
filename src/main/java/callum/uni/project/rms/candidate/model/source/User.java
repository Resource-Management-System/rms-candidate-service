package callum.uni.project.rms.candidate.model.source;


import callum.uni.project.rms.candidate.model.BaseLocation;
import callum.uni.project.rms.candidate.model.RoleType;
import callum.uni.project.rms.candidate.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "sso_id")
    private String ssoId;

    @Column(name = "current_role_id")
    private Long currentRoleId;
    
    @Column(name = "user_type")
    private UserType userType;

    @Column(name = "user_specialism")
    private RoleType userSpecialism;
    
    @Column(name = "base_location")
    private BaseLocation baseLocation;

    @Column(name = "business_unit_id")
    private Long businessUnitId;

    @Column(name = "grade")
    private String grade;
}
