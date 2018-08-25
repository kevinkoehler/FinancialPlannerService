package app.kevnet.fps.controller;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.Plan;
import app.kevnet.fps.service.EntryService;
import app.kevnet.fps.service.PlanService;
import app.kevnet.fps.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(PlanController.class)
public class PlanControllerTest {

  private static final String REQUEST_MAPPING = "/plan";
  private static final long ID = 1L;

  private Plan plan;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private PlanService planService;

  @MockBean
  private EntryService entryService;

  @Before
  public void setUp() {
    plan = TestUtil.getPlan();
  }

  private ResultActions comparePlan(ResultActions result) throws Exception {
    return result.andExpect(MockMvcResultMatchers
        .jsonPath("$.id", Matchers.is(plan.getId())))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.name", Matchers.is(plan.getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.currentSavings",
            Matchers.is(plan.getCurrentSavings().intValue())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.goalSavings",
            Matchers.is(plan.getGoalSavings().intValue())));
  }

  private ResultActions comparePlanArray(ResultActions result)
      throws Exception {
    return result.andExpect(MockMvcResultMatchers
        .jsonPath("$[0].id", Matchers.is(plan.getId())))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$[0].name", Matchers.is(plan.getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].currentSavings",
            Matchers.is(plan.getCurrentSavings().intValue())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].goalSavings",
            Matchers.is(plan.getGoalSavings().intValue())));
  }

  private ResultActions compareEntryArray(ResultActions result, Entry entry)
      throws Exception {
    return result.andExpect(MockMvcResultMatchers
        .jsonPath("$[0].id", Matchers.is(entry.getId())))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$[0].planId", Matchers.is(entry.getPlanId())))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$[0].name", Matchers.is(entry.getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].amount",
            Matchers.is(entry.getAmount().intValue())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].type",
            Matchers.is(entry.getType().toString())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].frequency",
            Matchers.is(entry.getFrequency().toString())));
  }

  @Test
  public void testGetAllPlans() throws Exception {
    List<Plan> expected = Collections.singletonList(plan);
    Mockito.when(planService.findAll()).thenReturn(expected);
    ResultActions result = mvc
        .perform(MockMvcRequestBuilders.get(REQUEST_MAPPING))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    comparePlanArray(result);
  }

  @Test
  public void testGetAllPlansNull() throws Exception {
    Mockito.when(planService.findAll()).thenReturn(Collections.EMPTY_LIST);
    mvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void testSavePlan() throws Exception {
    Mockito.when(planService.save(plan)).thenReturn(plan);
    ResultActions result = mvc
        .perform(MockMvcRequestBuilders.post(REQUEST_MAPPING)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(plan)))
        .andExpect(MockMvcResultMatchers.status().isOk());
    comparePlan(result);
  }

  @Test
  public void testSavePlanNull() throws Exception {
    Mockito.when(planService.save(plan)).thenReturn(null);
    mvc.perform(
        MockMvcRequestBuilders.put(REQUEST_MAPPING, plan)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(plan)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void deletePlan() throws Exception {
    Mockito.when(planService.findById(ID))
        .thenReturn((plan));
    mvc.perform(MockMvcRequestBuilders.delete(REQUEST_MAPPING + "/" + ID))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void deletePlanNull() throws Exception {
    Mockito.when(planService.findById(ID)).thenReturn(null);
    mvc.perform(MockMvcRequestBuilders.delete(REQUEST_MAPPING + "/" + ID))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testGetEntriesByPlanId() throws Exception {
    Entry entry = TestUtil.getEntry();
    Mockito.when(entryService.findByPlanId(ID))
        .thenReturn(Collections.singletonList(entry));
    ResultActions result = mvc.perform(
        MockMvcRequestBuilders.get(REQUEST_MAPPING + "/" + ID + "/entries"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    compareEntryArray(result, entry);
  }

  @Test
  public void testGetEntriesByPlanIdNull() throws Exception {
    Mockito.when(entryService.findByPlanId(ID)).thenReturn(null);
    ResultActions result = mvc.perform(
        MockMvcRequestBuilders.get(REQUEST_MAPPING + "/" + ID + "/entries"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testGetEntriesByPlanIdEmpty() throws Exception {
    Mockito.when(entryService.findByPlanId(ID))
        .thenReturn(Collections.EMPTY_LIST);
    ResultActions result = mvc.perform(
        MockMvcRequestBuilders.get(REQUEST_MAPPING + "/" + ID + "/entries"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

}
