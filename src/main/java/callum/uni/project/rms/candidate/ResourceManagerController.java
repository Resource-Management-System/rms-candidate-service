package callum.uni.project.rms.candidate;

import callum.uni.project.rms.candidate.model.request.CreateResourceManager;
import callum.uni.project.rms.candidate.model.target.BuId;
import callum.uni.project.rms.candidate.model.target.RmNames;
import callum.uni.project.rms.candidate.model.target.TargetResourceManager;
import callum.uni.project.rms.candidate.model.target.TargetUser;
import callum.uni.project.rms.candidate.service.ResourceManagerService;
import callum.uni.project.rms.candidate.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
public class ResourceManagerController {

    private final ResourceManagerService resourceManagerService;
    private final UserService userService;

    @GetMapping("/resourceManager/bu")
    @ResponseStatus(HttpStatus.OK)
    public BuId getResourceManagerBu(@RequestParam @NonNull Long rmId) {
        return resourceManagerService.retrieveResourceManagerBu(rmId);
    }

    @GetMapping(value = "/resourceManager/fullName", params = "buId")
    @ResponseStatus(HttpStatus.OK)
    public RmNames getResourceManageName(@RequestParam("buId") Long buId) {
        List<Long> rmIds = resourceManagerService.retrieveResourceManagerIdByBu(buId);

        List<String> resourceManagerNames = rmIds.stream().map(userService::retrieveUserByInternalId)
                .map(TargetUser::getFullName).collect(Collectors.toList());
        
        return RmNames.builder()
                .resourceManagerNames(resourceManagerNames)
                .build();
    }
    
    @PostMapping(value = "/resourceManager")
    @ResponseStatus(HttpStatus.CREATED)
    public TargetResourceManager postResourceManager(@RequestBody @NonNull CreateResourceManager req){
        return resourceManagerService.createResourceManager(req.getRmId(), req.getBuId());
    }
}
