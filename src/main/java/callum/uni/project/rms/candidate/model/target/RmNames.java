package callum.uni.project.rms.candidate.model.target;

import callum.project.uni.rms.parent.model.AbstractServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class RmNames extends AbstractServiceResponse {

    private List<String> resourceManagerNames;
}
