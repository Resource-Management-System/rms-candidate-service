package callum.uni.project.rms.candidate.service;


import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.uni.project.rms.candidate.mapper.BusinessUnitMapper;
import callum.uni.project.rms.candidate.model.source.BusinessUnit;
import callum.uni.project.rms.candidate.model.target.TargetBusinessUnitList;
import callum.uni.project.rms.candidate.service.repository.BusinessUnitRepository;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class BusinessUnitService {

    private final BusinessUnitRepository businessUnitRepository;

    public TargetBusinessUnitList retrieveBusinessUnits(){
        try {
            List<BusinessUnit> businessUnitList = Lists.newArrayList(businessUnitRepository.findAll());

            return new TargetBusinessUnitList(businessUnitList.stream().map(BusinessUnitMapper::mapDbToTarget)
                    .collect(Collectors.toList()));
        } catch (HibernateException e){
            log.error(e.getMessage());
            throw new InternalServiceException("Error retrieving business units", e);
        }

    }
}
