package callum.uni.project.rms.candidate;

import callum.uni.project.rms.candidate.model.target.TargetBusinessUnitList;
import callum.uni.project.rms.candidate.service.BusinessUnitService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
public class BusinessUnitController {

    private final BusinessUnitService businessUnitService;

    @GetMapping("/businessUnits")
    @ResponseStatus(HttpStatus.OK)
    public TargetBusinessUnitList getBusinessUnits(){
        return businessUnitService.retrieveBusinessUnits();
    }

}
