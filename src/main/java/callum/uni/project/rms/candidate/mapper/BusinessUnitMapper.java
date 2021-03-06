package callum.uni.project.rms.candidate.mapper;

import callum.uni.project.rms.candidate.model.source.BusinessUnit;
import callum.uni.project.rms.candidate.model.target.TargetBusinessUnit;

public class BusinessUnitMapper {

    public static TargetBusinessUnit mapDbToTarget(BusinessUnit dbModel){
        return TargetBusinessUnit.builder()
                .id(dbModel.getId())
                .name(dbModel.getName())
                .build();
    }
}
