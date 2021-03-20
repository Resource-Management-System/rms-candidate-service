package callum.uni.project.rms.candidate;

import callum.project.uni.rms.parent.exception.InternalServiceException;
import callum.uni.project.rms.candidate.model.target.TargetBusinessUnit;
import callum.uni.project.rms.candidate.model.target.TargetBusinessUnitList;
import callum.uni.project.rms.candidate.service.BusinessUnitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BusinessUnitController.class)
class BusinessUnitControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BusinessUnitService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getBusinessUnits_happyPath() throws Exception {

        TargetBusinessUnitList mockRes = new TargetBusinessUnitList();
        mockRes.setBusinessUnitList(List.of(new TargetBusinessUnit(1L, "BU")));

        when(service.retrieveBusinessUnits())
                .thenReturn(mockRes);

        ResultActions resultActions = this.mvc.perform(get("/businessUnits"))
                .andDo(print())
                .andExpect(status().isOk());

        MvcResult result = resultActions.andReturn();
        String contentAsString = result.getResponse().getContentAsString();
        TargetBusinessUnitList res = objectMapper.readValue(contentAsString, TargetBusinessUnitList.class);
        List<TargetBusinessUnit> bus = res.getBusinessUnitList();
        assertEquals(1, bus.size());
        TargetBusinessUnit bu = bus.get(0);
        assertEquals(1L, bu.getId());
        assertEquals("BU", bu.getName());
    }

    @Test
    void getBusinessUnits_internalServerError() throws Exception {
        
        when(service.retrieveBusinessUnits())
                .thenThrow(InternalServiceException.class);

         this.mvc.perform(get("/businessUnits"))
                .andDo(print())
                .andExpect(status().is5xxServerError());
    }
}