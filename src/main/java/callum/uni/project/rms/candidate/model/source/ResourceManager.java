package callum.uni.project.rms.candidate.model.source;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "resource_manager")
public class ResourceManager {

    @Id
    private Long userId;

    private Long businessUnitId;
}
