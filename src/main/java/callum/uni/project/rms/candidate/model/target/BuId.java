package callum.uni.project.rms.candidate.model.target;

import callum.project.uni.rms.parent.model.AbstractServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuId extends AbstractServiceResponse {

    private Long buId;
}
