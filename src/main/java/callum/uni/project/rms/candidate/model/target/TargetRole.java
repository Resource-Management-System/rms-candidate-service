package callum.uni.project.rms.candidate.model.target;

import callum.project.uni.rms.parent.model.AbstractServiceResponse;
import callum.uni.project.rms.candidate.model.BaseLocation;
import callum.uni.project.rms.candidate.model.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@AllArgsConstructor
@Builder
@Data
@ToString
public class TargetRole extends AbstractServiceResponse {

    private Long id;

    private String roleName;

    private String accountName;

    private String projectName;

    private RoleType roleType;

    private LocalDate startDate;

    private LocalDate endDate;

    private String projectCode;

    private String accountNumber;

    private String description;

    private BaseLocation baseLocation;

    private double baseLocationX;

    private double baseLocationY;

    private Boolean isRoleOpen;

    private String assignee;

    private Long businessUnit;
}
