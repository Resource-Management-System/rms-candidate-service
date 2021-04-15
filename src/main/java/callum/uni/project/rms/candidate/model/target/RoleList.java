package callum.uni.project.rms.candidate.model.target;

import callum.project.uni.rms.parent.model.AbstractServiceResponse;
import lombok.*;

import java.util.List;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleList extends AbstractServiceResponse {

    private List<TargetRole> roleList;

}
