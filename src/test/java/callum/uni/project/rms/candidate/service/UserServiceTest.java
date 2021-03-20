package callum.uni.project.rms.candidate.service;

import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import callum.uni.project.rms.candidate.model.BaseLocation;
import callum.uni.project.rms.candidate.model.RoleType;
import callum.uni.project.rms.candidate.model.UserType;
import callum.uni.project.rms.candidate.model.source.User;
import callum.uni.project.rms.candidate.model.target.Candidates;
import callum.uni.project.rms.candidate.model.target.TargetUser;
import callum.uni.project.rms.candidate.service.repository.UserRepository;
import org.assertj.core.util.Lists;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserService userService;

    private UserRepository userRepository;

    private static final  Long USER_ID = 3L;
    private static final Long BU_ID = 2L;

    @BeforeEach
    public void setup(){
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void updateUserProjectDetails() {
        userService.updateUserProjectDetails(USER_ID, 4L);

        verify(userRepository, times(1))
                .updateUserCurrentRoleId(USER_ID, 4L);
    }

    @Test
    void updateUserProjectDetails_serverError() {
        doThrow(new HibernateException("ex", new HibernateException("ex")))
                .when(userRepository).updateUserCurrentRoleId(eq(USER_ID),eq( 4L));

        assertThrows(InternalServiceException.class,
                () -> userService.updateUserProjectDetails(USER_ID, 4L));
    }



    @Test
    void retrieveUserByFullName() {
        when(userRepository.findAllByFullNameContaining(eq("fullName"))).thenReturn(List.of(buildUser()));

        Candidates res = userService.retrieveUserByFullName("fullName");

        assertEquals(1, res.getCandidates().size());
        assertEquals("fullName", res.getCandidates().get(0).getFullName());
    }

    @Test
    void retrieveUserByFullName_noneFound() {
        when(userRepository.findAllByFullNameContaining(eq("fullName"))).thenReturn(Lists.emptyList());

        Candidates res = userService.retrieveUserByFullName("fullName");

        assertEquals(0, res.getCandidates().size());
    }


    @Test
    void retrieveUserByFullName_serverError() {
        when(userRepository.findAllByFullNameContaining(eq("fullName")))
                .thenThrow(HibernateException.class);

        assertThrows(InternalServiceException.class, () -> userService.retrieveUserByFullName("fullName"));
    }

    private User buildUser(){
        return User.builder()
                .baseLocation(BaseLocation.ASTON)
                .businessUnitId(2L)
                .currentRoleId(4L)
                .fullName("fullName")
                .grade("A1")
                .id(USER_ID)
                .ssoId("1")
                .userSpecialism(RoleType.PROJECT_MANAGER)
                .userType(UserType.PROJECT_MANAGER)
                .build();
    }

    @Test
    void retrieveAllByBusinessUnit() {
        when(userRepository.findAllByBusinessUnitId(eq(BU_ID))).thenReturn(List.of(buildUser()));

        Candidates res = userService.retrieveAllByBusinessUnit(BU_ID);

        assertEquals(1, res.getCandidates().size());
        assertEquals("fullName", res.getCandidates().get(0).getFullName());

    }

    @Test
    void retrieveAllByBusinessUnit_serverError() {
        when(userRepository.findAllByBusinessUnitId(eq(BU_ID)))
                .thenThrow(HibernateException.class);

        assertThrows(InternalServiceException.class,
                () -> userService.retrieveAllByBusinessUnit(BU_ID));
    }


    @Test
    void retrieveAllByBusinessUnit_noneFound() {
        when(userRepository.findAllByBusinessUnitId(eq(BU_ID))).thenReturn(Lists.emptyList());

        Candidates res = userService.retrieveAllByBusinessUnit(BU_ID);

        assertEquals(0, res.getCandidates().size());
    }


    @Test
    void retrieveUserByInternalId() {
        when(userRepository.findById(eq(USER_ID))).thenReturn(Optional.of(buildUser()));

        TargetUser res = userService.retrieveUserByInternalId(USER_ID);

        assertEquals("fullName", res.getFullName());
    }

    @Test
    void retrieveUserByInternalId_serverError() {
        when(userRepository.findById(eq(USER_ID)))
                .thenThrow(HibernateException.class);

        assertThrows(InternalServiceException.class,
                () -> userService.retrieveUserByInternalId(USER_ID));
    }

    @Test
    void retrieveUserByInternalId_notFound() {
        when(userRepository.findById(eq(USER_ID)))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> userService.retrieveUserByInternalId(USER_ID));
    }
}