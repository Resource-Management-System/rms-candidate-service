package callum.uni.project.rms.candidate.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateResourceManager {

    @JsonProperty("rmId")
    private Long rmId;

    @JsonProperty("businessUnitId")
    private Long buId;

}
