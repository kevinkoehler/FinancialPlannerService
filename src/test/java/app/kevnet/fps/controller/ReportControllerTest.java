package app.kevnet.fps.controller;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.Report;
import app.kevnet.fps.service.EntryService;
import app.kevnet.fps.service.ReportService;
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
@WebMvcTest(ReportController.class)
public class ReportControllerTest {

  private static final String REQUEST_MAPPING = "/report";
  private static final long ID = 1L;

  private Report report;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ReportService reportService;

  @MockBean
  private EntryService entryService;

  @Before
  public void setUp() {
    report = TestUtil.getReport();
  }

  private ResultActions compareReport(ResultActions result) throws Exception {
    return result.andExpect(MockMvcResultMatchers
        .jsonPath("$.id", Matchers.is(report.getId())))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.name", Matchers.is(report.getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.currentSavings",
            Matchers.is(report.getCurrentSavings().intValue())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.goalSavings",
            Matchers.is(report.getGoalSavings().intValue())));
  }

  private ResultActions compareReportArray(ResultActions result)
      throws Exception {
    return result.andExpect(MockMvcResultMatchers
        .jsonPath("$[0].id", Matchers.is(report.getId())))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$[0].name", Matchers.is(report.getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].currentSavings",
            Matchers.is(report.getCurrentSavings().intValue())))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].goalSavings",
            Matchers.is(report.getGoalSavings().intValue())));
  }

  private ResultActions compareEntryArray(ResultActions result, Entry entry)
      throws Exception {
    return result.andExpect(MockMvcResultMatchers
        .jsonPath("$[0].id", Matchers.is(entry.getId())))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$[0].reportId", Matchers.is(entry.getReportId())))
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
  public void testGetAllReports() throws Exception {
    List<Report> expected = Collections.singletonList(report);
    Mockito.when(reportService.findAll()).thenReturn(expected);
    ResultActions result = mvc
        .perform(MockMvcRequestBuilders.get(REQUEST_MAPPING))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    compareReportArray(result);
  }

  @Test
  public void testGetAllReportsNull() throws Exception {
    Mockito.when(reportService.findAll()).thenReturn(Collections.EMPTY_LIST);
    mvc.perform(MockMvcRequestBuilders.get(REQUEST_MAPPING))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void testSaveReport() throws Exception {
    Mockito.when(reportService.save(report)).thenReturn(report);
    ResultActions result = mvc
        .perform(MockMvcRequestBuilders.post(REQUEST_MAPPING)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(report)))
        .andExpect(MockMvcResultMatchers.status().isOk());
    compareReport(result);
  }

  @Test
  public void testSaveReportNull() throws Exception {
    Mockito.when(reportService.save(report)).thenReturn(null);
    mvc.perform(
        MockMvcRequestBuilders.put(REQUEST_MAPPING, report)
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(report)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void deleteReport() throws Exception {
    Mockito.when(reportService.findById(ID))
        .thenReturn((report));
    mvc.perform(MockMvcRequestBuilders.delete(REQUEST_MAPPING + "/" + ID))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void deleteReportNull() throws Exception {
    Mockito.when(reportService.findById(ID)).thenReturn(null);
    mvc.perform(MockMvcRequestBuilders.delete(REQUEST_MAPPING + "/" + ID))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testGetEntriesByReportId() throws Exception {
    Entry entry = TestUtil.getEntry();
    Mockito.when(entryService.findByReportId(ID))
        .thenReturn(Collections.singletonList(entry));
    ResultActions result = mvc.perform(
        MockMvcRequestBuilders.get(REQUEST_MAPPING + "/" + ID + "/entries"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    compareEntryArray(result, entry);
  }

  @Test
  public void testGetEntriesByReportIdNull() throws Exception {
    Mockito.when(entryService.findByReportId(ID)).thenReturn(null);
    ResultActions result = mvc.perform(
        MockMvcRequestBuilders.get(REQUEST_MAPPING + "/" + ID + "/entries"))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void testGetEntriesByReportIdEmpty() throws Exception {
    Mockito.when(entryService.findByReportId(ID))
        .thenReturn(Collections.EMPTY_LIST);
    ResultActions result = mvc.perform(
        MockMvcRequestBuilders.get(REQUEST_MAPPING + "/" + ID + "/entries"))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

}
