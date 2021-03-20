package callum.uni.project.rms.candidate.mapper;


import callum.uni.project.rms.candidate.model.source.User;
import callum.uni.project.rms.candidate.model.target.TargetUser;

public class UserMapper {

    public static TargetUser mapDbModelToTarget(User user){

        return TargetUser.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .currentRoleId(user.getCurrentRoleId())
                .userType(user.getUserType())
                .baseLocation(user.getBaseLocation())
                .baseLocationX(user.getBaseLocation().getX())
                .businessUnitId(user.getBusinessUnitId())
                .baseLocationY(user.getBaseLocation().getY())
                .userSpecialism(user.getUserSpecialism())
                .grade(user.getGrade())
                .build();
    }
}
