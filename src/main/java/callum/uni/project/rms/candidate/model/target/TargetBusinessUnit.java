package callum.uni.project.rms.candidate.model.target;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class TargetBusinessUnit {

    private Long id;
    private String name;
}
