package callum.uni.project.rms.candidate.service.repository;

import callum.uni.project.rms.candidate.model.source.ResourceManager;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceManagerRepository extends CrudRepository<ResourceManager, Long> {

    Optional<ResourceManager> findByBusinessUnitId(Long buId);

}
