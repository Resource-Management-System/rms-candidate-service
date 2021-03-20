package callum.uni.project.rms.candidate;

import callum.uni.project.rms.candidate.model.BaseLocation;
import callum.uni.project.rms.candidate.model.RoleType;
import callum.uni.project.rms.candidate.model.UserType;
import callum.uni.project.rms.candidate.model.target.TargetUser;

public class TargetUserBuilder {

    public static TargetUser buildTargetUser(){
        return TargetUser.builder()
                .grade("A1")
                .baseLocation(BaseLocation.ASTON)
                .businessUnitId(2L)
                .fullName("fullName")
                .currentRoleId(1L)
                .id(3L)
                .resourceManagerName("Resource Manager")
                .userSpecialism(RoleType.PROJECT_MANAGER)
                .userType(UserType.PROJECT_MANAGER)
                .build();

    }
}
