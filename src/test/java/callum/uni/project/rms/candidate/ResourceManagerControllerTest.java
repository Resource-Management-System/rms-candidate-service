package callum.uni.project.rms.candidate;

import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.project.uni.rms.parent.exception.NotFoundException;
import callum.uni.project.rms.candidate.model.target.BuId;
import callum.uni.project.rms.candidate.model.target.RmNames;
import callum.uni.project.rms.candidate.model.target.TargetResourceManager;
import callum.uni.project.rms.candidate.model.target.TargetUser;
import callum.uni.project.rms.candidate.service.ResourceManagerService;
import callum.uni.project.rms.candidate.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ResourceManagerController.class)
class ResourceManagerControllerTest {


    @Autowired
    private MockMvc mvc;

    @MockBean
    private ResourceManagerService service;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private static final Long RM_ID = 1L;

    private static final Long BU_ID = 2L;


    @Test
    void getResourceManagerBu_happyPath() throws Exception {

        BuId mockRes = new BuId(BU_ID);

        when(service.retrieveResourceManagerBu(eq(RM_ID)))
                .thenReturn(mockRes);

        ResultActions resultActions = this.mvc.perform(get("/resourceManager/bu")
                .queryParam("rmId", RM_ID.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        BuId res = objectMapper.readValue(contentAsString, BuId.class);

        assertEquals(BU_ID, res.getBuId());
    }

    @Test
    void getResourceManagerBu_notFound() throws Exception {

        when(service.retrieveResourceManagerBu(eq(RM_ID)))
                .thenThrow(NotFoundException.class);

        this.mvc.perform(get("/resourceManager/bu").queryParam("rmId",
                RM_ID.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getResourceManagerBu_serverError() throws Exception {

        when(service.retrieveResourceManagerBu(eq(RM_ID)))
                .thenThrow(InternalServiceException.class);

        this.mvc.perform(get("/resourceManager/bu").queryParam("rmId",
                RM_ID.toString()))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    void getResourceManageName_happyPath() throws Exception {

        when(service.retrieveResourceManagerIdByBu(eq(BU_ID))).thenReturn(List.of(RM_ID));
        TargetUser mockRes = TargetUser.builder()
                .businessUnitId(BU_ID)
                .fullName("mockRes")
                .build();

        when(userService.retrieveUserByInternalId(eq(RM_ID)))
                .thenReturn(mockRes);


        ResultActions resultActions = this.mvc.perform(get("/resourceManager/fullName")
                .queryParam("buId", BU_ID.toString()))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        RmNames res = objectMapper.readValue(contentAsString, RmNames.class);
        assertEquals("mockRes", res.getResourceManagerNames().get(0));

        verify(userService, times(1)).retrieveUserByInternalId(eq(RM_ID));
    }

    @Test
    void getResourceManageName_notFound() throws Exception {

        when(service.retrieveResourceManagerIdByBu(eq(BU_ID)))
                .thenThrow(NotFoundException.class);

        this.mvc.perform(get("/resourceManager/fullName")
                .queryParam("buId", BU_ID.toString()))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    void getResourceManageName_serverError() throws Exception {


        when(service.retrieveResourceManagerIdByBu(eq(BU_ID)))
                .thenThrow(InternalServiceException.class);

        this.mvc.perform(get("/resourceManager/fullName")
                .queryParam("buId", BU_ID.toString()))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }

    @Test
    void postResourceManager_happyPath() throws Exception {
        TargetResourceManager mockRes = new TargetResourceManager(RM_ID, BU_ID);

        when(service.createResourceManager(eq(RM_ID), eq(BU_ID)))
                .thenReturn(mockRes);

        ResultActions resultActions = this.mvc.perform(post("/resourceManager")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"rmId\":\""+RM_ID+"\", " +
                        "\"businessUnitId\": " +BU_ID + "}"))
                .andDo(print())
                .andExpect(status().isCreated());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        TargetResourceManager res = objectMapper.readValue(contentAsString, TargetResourceManager.class);
        assertEquals(BU_ID, res.getBusinessUnitId());
        assertEquals(RM_ID, res.getUserId());
    }
    @Test
    void postResourceManager_serverError() throws Exception {
        when(service.createResourceManager(eq(RM_ID), eq(BU_ID)))
                .thenThrow(InternalServiceException.class);

        this.mvc.perform(post("/resourceManager")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{" +
                        "\"rmId\":\""+RM_ID+"\", " +
                        "\"businessUnitId\": " +BU_ID + "}"))
                .andDo(print())
                .andExpect(status().isInternalServerError());
    }
}