package callum.uni.project.rms.candidate.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class UpdateUserCurrentRole {

    @JsonProperty("userId")
    private Long userId;

    @JsonProperty("roleId")
    private Long roleId;
}
