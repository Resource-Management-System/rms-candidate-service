package callum.uni.project.rms.candidate.service;

import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import callum.uni.project.rms.candidate.model.source.ResourceManager;
import callum.uni.project.rms.candidate.model.target.BuId;
import callum.uni.project.rms.candidate.model.target.TargetResourceManager;
import callum.uni.project.rms.candidate.service.repository.ResourceManagerRepository;
import org.assertj.core.util.Lists;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ResourceManagerServiceTest {

    private ResourceManagerService resourceManagerService;

    private ResourceManagerRepository resourceManagerRepository;

    private static final Long RM_ID = 1L;
    private static final Long BU_ID = 2L;

    @BeforeEach
    public void setup(){
        resourceManagerRepository = mock(ResourceManagerRepository.class);
        resourceManagerService = new ResourceManagerService(resourceManagerRepository);
    }

    @Test
    void retrieveResourceManagerBu() {
        when(resourceManagerRepository.findById(RM_ID)).thenReturn(Optional.of(ResourceManager.builder()
                .businessUnitId(BU_ID).userId(RM_ID).build()));

        BuId res = resourceManagerService.retrieveResourceManagerBu(RM_ID);
        assertEquals(BU_ID, res.getBuId());
    }

    @Test
    void retrieveResourceManagerBu_empty() {
        when(resourceManagerRepository.findById(RM_ID)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () ->
                resourceManagerService.retrieveResourceManagerBu(RM_ID));
    }

    @Test
    void retrieveResourceManagerBu_serverError() {
        when(resourceManagerRepository.findById(RM_ID))
                .thenThrow(HibernateException.class);

        assertThrows(InternalServiceException.class, () ->
                resourceManagerService.retrieveResourceManagerBu(RM_ID));
    }

    @Test
    void retrieveResourceManagerIdByBu() {

        when(resourceManagerRepository.findAllByBusinessUnitId(eq(BU_ID)))
                .thenReturn(List.of(ResourceManager.builder()
                        .userId(RM_ID).businessUnitId(BU_ID).build()));

        List<Long> res = resourceManagerService.retrieveResourceManagerIdByBu(BU_ID);
        assertEquals(1, res.size());
        assertEquals(RM_ID, res.get(0));
    }

    @Test
    void retrieveResourceManagerIdByBu_empty() {

        when(resourceManagerRepository.findAllByBusinessUnitId(eq(BU_ID)))
                .thenReturn(Lists.emptyList());

        List<Long> res = resourceManagerService.retrieveResourceManagerIdByBu(BU_ID);
        assertEquals(0,res.size() );
    }


    @Test
    void retrieveResourceManagerIdByBu_serverError() {

        when(resourceManagerRepository.findAllByBusinessUnitId(eq(BU_ID)))
                .thenThrow(HibernateException.class);

        assertThrows(InternalServiceException.class,
                () -> resourceManagerService.retrieveResourceManagerIdByBu(BU_ID));
    }


    @Test
    void createResourceManager() {
        when(resourceManagerRepository.save(eq(new ResourceManager(RM_ID, BU_ID))))
                .thenReturn(new ResourceManager(RM_ID, BU_ID));

        TargetResourceManager res = resourceManagerService.createResourceManager(RM_ID,  BU_ID);

        assertEquals(RM_ID, res.getUserId());
        assertEquals(BU_ID, res.getBusinessUnitId());
    }

    @Test
    void createResourceManager_serverError() {
        when(resourceManagerRepository.save(eq(new ResourceManager(RM_ID, BU_ID))))
                .thenThrow(HibernateException.class);

        assertThrows(InternalServiceException.class,
                () -> resourceManagerService.createResourceManager(RM_ID,  BU_ID)) ;
    }
}