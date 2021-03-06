package callum.uni.project.rms.candidate.service;

import callum.project.uni.rms.parent.exception.NotFoundException;
import callum.uni.project.rms.candidate.model.source.ResourceManager;
import callum.uni.project.rms.candidate.model.target.BuId;
import callum.uni.project.rms.candidate.service.repository.ResourceManagerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ResourceManagerService {

    private final ResourceManagerRepository resourceManagerRepository;

    public BuId retrieveResourceManagerBu(Long rmId){
        Optional<ResourceManager> rm = resourceManagerRepository.findById(rmId);
        Long buId =  rm.orElseThrow(() -> new NotFoundException("Rm not found")).getBusinessUnitId();
        return new BuId(buId);
    }

    public long retrieveResourceManagerIdByBu(Long bu){
        Optional<ResourceManager> rm = resourceManagerRepository.findByBusinessUnitId(bu);
        return rm.orElseThrow(() -> new NotFoundException("Rm not found")).getBusinessUnitId();
    }
    
    // As a resource manager I need to be able to shortlist candidates for roles that they
    // have not applied for

    // As a resource manager I need to be able to see who / when candidates are available

    // As a resource manager I would like to be able search through candidates based on their skills.

    // As a project manager I need to be able to set the 'certainty of a role'

    // As a candidate or a resource manager I would like to be able to see how certain a role is.

}
