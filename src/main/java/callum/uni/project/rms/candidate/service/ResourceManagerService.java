package callum.uni.project.rms.candidate.service;

import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import callum.uni.project.rms.candidate.model.source.ResourceManager;
import callum.uni.project.rms.candidate.model.target.BuId;
import callum.uni.project.rms.candidate.model.target.TargetResourceManager;
import callum.uni.project.rms.candidate.service.repository.ResourceManagerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ResourceManagerService {

    private final ResourceManagerRepository resourceManagerRepository;

    public BuId retrieveResourceManagerBu(Long rmId){
        try {
            Optional<ResourceManager> rm = resourceManagerRepository.findById(rmId);
            Long buId = rm.orElseThrow(() -> new NotFoundException("Rm not found")).getBusinessUnitId();
            return new BuId(buId);
        } catch (HibernateException e){
            throw new InternalServiceException("Error retrieving resource manager", e);
        }
    }

    public List<Long> retrieveResourceManagerIdByBu(Long bu){
        try {
            List<ResourceManager> rm = resourceManagerRepository.findAllByBusinessUnitId(bu);
            return rm.stream().map(ResourceManager::getUserId).collect(Collectors.toList());
        } catch (HibernateException e){
            throw new InternalServiceException("Error retrieving resource manager", e);
        }
    }

    public TargetResourceManager createResourceManager(Long rmId, Long buId){
        try {
            ResourceManager resourceManager = new ResourceManager(rmId, buId);
            ResourceManager created = resourceManagerRepository.save(resourceManager);
            return new TargetResourceManager(created.getUserId(), created.getBusinessUnitId());
        } catch (HibernateException e){
            throw new InternalServiceException("Error retrieving resource manager", e);
        }
    }
}
