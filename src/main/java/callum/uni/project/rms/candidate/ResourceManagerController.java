package callum.uni.project.rms.candidate;

import callum.uni.project.rms.candidate.model.target.BuId;
import callum.uni.project.rms.candidate.model.target.RmName;
import callum.uni.project.rms.candidate.service.ResourceManagerService;
import callum.uni.project.rms.candidate.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@Slf4j
@AllArgsConstructor
public class ResourceManagerController {

    private final ResourceManagerService resourceManagerService;
    private final UserService userService;

    @GetMapping("/resourceManager/bu")
    @ResponseStatus(HttpStatus.OK)
    public BuId retrieveResourceManagerId(@RequestParam @NonNull Long rmId) {
        return resourceManagerService.retrieveResourceManagerBu(rmId);
    }

    @GetMapping(value = "/resourceManager/fullName", params = "buId")
    @ResponseStatus(HttpStatus.OK)
    public RmName getResourceManageName(@RequestParam("buId") Long buId) {
        Long rmId = resourceManagerService.retrieveResourceManagerIdByBu(buId);
        return RmName.builder()
                .fullName(userService.retrieveUserByInternalId(rmId)
                        .getFullName())
                .build();
    }
}
