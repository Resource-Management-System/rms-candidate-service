package callum.uni.project.rms.candidate;

import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import callum.uni.project.rms.candidate.model.target.BuId;
import callum.uni.project.rms.candidate.model.target.Candidates;
import callum.uni.project.rms.candidate.model.target.TargetUser;
import callum.uni.project.rms.candidate.service.ResourceManagerService;
import callum.uni.project.rms.candidate.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static callum.uni.project.rms.candidate.TargetUserBuilder.buildTargetUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CandidateController.class)
class CandidateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ResourceManagerService resourceManagerService;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Long RM_ID = 1L;

    private static final Long BU_ID = 2L;

    private static final Long USER_ID = 3L;

    private static final String FULL_NAME = "fullName";


    @Test
    void retrieveCandidatesByResourceManager_happyPath() throws Exception {
        BuId mockRes = new BuId(BU_ID);

        when(resourceManagerService.retrieveResourceManagerBu(eq(RM_ID)))
                .thenReturn(mockRes);

        Candidates candidates = Candidates.builder()
                .candidates(List.of(buildTargetUser()))
                .build();

        when(userService.retrieveAllByBusinessUnit(eq(BU_ID)))
                .thenReturn(candidates);

        ResultActions resultActions = this.mvc.perform(get("/candidate/list")
                .queryParam("rmId", RM_ID.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Candidates res = objectMapper.readValue(contentAsString, Candidates.class);
        assertEquals(1, res.getCandidates().size());
        assertEquals(FULL_NAME, res.getCandidates().get(0).getFullName());
    }

    @Test
    void retrieveCandidatesByResourceManager_noCandidatesForBu() throws Exception {
        BuId mockRes = new BuId(BU_ID);

        when(resourceManagerService.retrieveResourceManagerBu(eq(RM_ID)))
                .thenReturn(mockRes);

        when(userService.retrieveAllByBusinessUnit(eq(BU_ID)))
                .thenReturn(Candidates.builder().candidates(Lists.emptyList()).build());

        ResultActions resultActions = this.mvc.perform(get("/candidate/list")
                .queryParam("rmId", RM_ID.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Candidates res = objectMapper.readValue(contentAsString, Candidates.class);
        assertEquals(0, res.getCandidates().size());
    }

    @Test
    void retrieveCandidatesByResourceManager_notRmForProvidedId() throws Exception {
        when(resourceManagerService.retrieveResourceManagerBu(eq(RM_ID)))
                .thenThrow(NotFoundException.class);

        when(userService.retrieveAllByBusinessUnit(eq(BU_ID)))
                .thenReturn(Candidates.builder().build());

        verify(userService, times(0)).retrieveAllByBusinessUnit(any());

       this.mvc.perform(get("/candidate/list")
                .queryParam("rmId", RM_ID.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveCandidateByUserId_happyPath() throws Exception  {
        TargetUser mockUser = buildTargetUser();

        when(userService.retrieveUserByInternalId(eq(USER_ID)))
                .thenReturn(mockUser);

        ResultActions resultActions = this.mvc.perform(get("/candidate/"+ USER_ID.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        TargetUser res = objectMapper.readValue(contentAsString, TargetUser.class);
        assertEquals(USER_ID, res.getId());
        assertEquals("fullName", res.getFullName());
    }

    @Test
    void retrieveCandidateByUserId_notFound() throws Exception  {
        when(userService.retrieveUserByInternalId(eq(USER_ID)))
                .thenThrow(NotFoundException.class);
        this.mvc.perform(get("/candidate/"+ USER_ID.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void retrieveCandidateByUserId_serverError() throws Exception  {
        when(userService.retrieveUserByInternalId(eq(USER_ID)))
                .thenThrow(InternalServiceException.class);
        this.mvc.perform(get("/candidate/"+ USER_ID.toString()))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }


    @Test
    void retrieveCandidatesByFullName_happyPath() throws Exception  {

        Candidates candidates = Candidates.builder()
                .candidates(List.of(buildTargetUser()))
                .build();

        when(userService.retrieveUserByFullName(eq(FULL_NAME)))
                .thenReturn(candidates);

        ResultActions resultActions = this.mvc.perform(get("/candidate/list")
                .queryParam("fullName", FULL_NAME))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        Candidates res = objectMapper.readValue(contentAsString, Candidates.class);
        assertEquals(1, res.getCandidates().size());
        assertEquals(FULL_NAME, res.getCandidates().get(0).getFullName());
    }

    @Test
    void setUserCurrentRole_happyPath() throws Exception  {

        this.mvc.perform(put("/candidate/currentRole")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"userId\":\""+USER_ID+"\", " +
                        "\"roleId\": " +2L + "}"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}