package callum.uni.project.rms.candidate.model.target;

import callum.project.uni.rms.parent.model.AbstractServiceResponse;
import callum.uni.project.rms.candidate.model.BaseLocation;
import callum.uni.project.rms.candidate.model.RoleType;
import callum.uni.project.rms.candidate.model.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Builder
@Data
public class TargetUser extends AbstractServiceResponse {

    private Long id;

    private Long currentRoleId;

    private UserType userType;

    private String fullName;

    private BaseLocation baseLocation;

    private double baseLocationX;

    private double baseLocationY;

    private RoleType userSpecialism;

    private String resourceManagerName;

    private Long businessUnitId;

    private String grade;
}
