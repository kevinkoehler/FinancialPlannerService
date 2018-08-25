package app.kevnet.fps.controller;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.service.EntryService;
import app.kevnet.fps.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@WebMvcTest(EntryController.class)
public class EntryControllerTest {

  private static final String REQUEST_MAPPING = "/entry";
  private static final long ID = 1L;

  private Entry entry;

  @Autowired
  private MockMvc mvc;

  @MockBean
  private EntryService entryService;

  @Before
  public void setUp() {
    entry = TestUtil.getEntry();
  }

  private ResultActions compareEntry(ResultActions result, Entry entry)
      throws Exception {
    return result.andExpect(MockMvcResultMatchers
        .jsonPath("$.id", Matchers.is(entry.getId())))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.planId", Matchers.is(entry.getPlanId())))
        .andExpect(MockMvcResultMatchers
            .jsonPath("$.name", Matchers.is(entry.getName())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.amount",
            Matchers.is(entry.getAmount().intValue())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.type",
            Matchers.is(entry.getType().toString())))
        .andExpect(MockMvcResultMatchers.jsonPath("$.frequency",
            Matchers.is(entry.getFrequency().toString())));
  }

  @Test
  public void testSaveEntry() throws Exception {
    Mockito.when(entryService.save(entry)).thenReturn(entry);
    ResultActions result = mvc
        .perform(MockMvcRequestBuilders.post(REQUEST_MAPPING).contentType(
            MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(entry)))
        .andExpect(MockMvcResultMatchers.status().isOk());
    compareEntry(result, entry);
  }

  @Test
  public void testSaveEntryNull() throws Exception {
    Mockito.when(entryService.save(entry)).thenReturn(null);
    mvc.perform(MockMvcRequestBuilders.put(REQUEST_MAPPING, entry)
        .contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(entry)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void deleteEntry() throws Exception {
    Mockito.when(entryService.findById(ID)).thenReturn(entry);
    mvc.perform(MockMvcRequestBuilders.delete(REQUEST_MAPPING + "/" + ID))
        .andExpect(MockMvcResultMatchers.status().isNoContent());
  }

  @Test
  public void deleteEntryNull() throws Exception {
    Mockito.when(entryService.findById(ID)).thenReturn(null);
    mvc.perform(MockMvcRequestBuilders.delete(REQUEST_MAPPING + "/" + ID))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

}
