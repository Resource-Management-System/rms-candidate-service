package callum.uni.project.rms.candidate.model.target;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TargetResourceManager {
    
    private Long userId;
    
    private Long businessUnitId;
}
