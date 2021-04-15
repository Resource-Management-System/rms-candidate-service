package callum.uni.project.rms.candidate.model.target;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TargetBusinessUnitList {

    private List<TargetBusinessUnit> businessUnitList;
}
