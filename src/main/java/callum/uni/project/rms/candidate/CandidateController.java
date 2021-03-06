package callum.uni.project.rms.candidate;

import callum.project.uni.rms.parent.model.AbstractServiceResponse;
import callum.uni.project.rms.candidate.model.request.UpdateUserCurrentRole;
import callum.uni.project.rms.candidate.model.target.BuId;
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
public class CandidateController {

    private final ResourceManagerService resourceManagerService;
    private final UserService userService;
    
    @GetMapping(value = "/candidate/list", params = "rmId")
    @ResponseStatus(HttpStatus.OK)
    public AbstractServiceResponse retrieveCandidatesByResourceManager(
            @RequestParam @NonNull Long rmId) {
        BuId bu = resourceManagerService.retrieveResourceManagerBu(rmId);
        return userService.retrieveAllByBusinessUnit(bu.getBuId());
    }
    
    @GetMapping(value = "/candidate/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public AbstractServiceResponse retrieveCandidateByUserId(
            @PathVariable @NonNull Long userId){
        return userService.retrieveUserByInternalId(userId);
    }

    @GetMapping(value = "/candidate/list", params = "fullName")
    @ResponseStatus(HttpStatus.OK)
    public AbstractServiceResponse retrieveCandidatesByFullName(
            @RequestParam @NonNull String fullName) {
        return userService.retrieveUserByFullName(fullName);
    }
    
    @PutMapping(value = "/candidate/currentRole")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void setUpdateUserCurrentRole(
            @RequestBody @NonNull UpdateUserCurrentRole updateUserCurrentRole){
        userService.updateUserProjectDetails(updateUserCurrentRole.getUserId(),
                updateUserCurrentRole.getRoleId());
    }
}
