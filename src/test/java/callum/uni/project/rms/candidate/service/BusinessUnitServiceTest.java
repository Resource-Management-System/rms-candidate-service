package callum.uni.project.rms.candidate.service;

import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.uni.project.rms.candidate.model.source.BusinessUnit;
import callum.uni.project.rms.candidate.model.target.TargetBusinessUnitList;
import callum.uni.project.rms.candidate.service.repository.BusinessUnitRepository;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BusinessUnitServiceTest {


    private BusinessUnitService businessUnitService;
    private BusinessUnitRepository businessUnitRepository;


    @BeforeEach
    private void setup(){
        businessUnitRepository = mock(BusinessUnitRepository.class);
        businessUnitService = new BusinessUnitService((businessUnitRepository));
    }

    @Test
    void retrieveBusinessUnits(){
        when(businessUnitRepository.findAll())
                .thenReturn(List.of(BusinessUnit.builder().name("ID").id(1L).build()));
        TargetBusinessUnitList res = businessUnitService.retrieveBusinessUnits();
        assertEquals(1, res.getBusinessUnitList().size());
        assertEquals("ID", res.getBusinessUnitList().get(0).getName());
        assertEquals(1L, res.getBusinessUnitList().get(0).getId());
    }

    @Test
    void retrieveBusinessUnits_noneFound(){
        when(businessUnitRepository.findAll())
                .thenReturn(new ArrayList<>());
        TargetBusinessUnitList res = businessUnitService.retrieveBusinessUnits();
        assertEquals(0, res.getBusinessUnitList().size());
    }

    @Test
    void retrieveBusinessUnits_severError(){
        when(businessUnitRepository.findAll())
                .thenThrow(new HibernateException("Error"));
        assertThrows(InternalServiceException.class, () -> businessUnitService.retrieveBusinessUnits());
    }
}