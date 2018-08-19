package app.kevnet.fps.service;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.Report;
import app.kevnet.fps.repository.EntryRepository;
import app.kevnet.fps.repository.ReportRepository;
import app.kevnet.fps.util.TestUtil;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class EntryServiceTest {

  private Entry entry;
  @Autowired
  private EntryService entryService;
  @MockBean
  private EntryRepository entryRepository;
  @MockBean
  private ReportRepository reportRepository;

  @Before
  public void setUp() {
    entry = TestUtil.getEntry();
  }

  @Test
  public void testFindById() {
    Mockito.when(entryRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(entry));
    Entry result = entryService.findById(Mockito.anyLong());
    Assert.assertEquals(entry, result);
  }

  @Test
  public void testFindByIdNull() {
    Mockito.when(entryRepository.findById(Mockito.anyLong())).thenReturn(null);
    Entry result = entryService.findById(Mockito.anyLong());
    Assert.assertNull(result);
  }

  @Test
  public void testSave() {
    Mockito.when(entryRepository.save(entry)).thenReturn(entry);
    Entry result = entryService.save(entry);
    Assert.assertEquals(entry, result);
  }

  @Test
  public void testDeleteById() {
    entryService.deleteById(Mockito.anyLong());
  }

  @Test
  public void testFindByReportId() {
    Report report = TestUtil.getReport();
    Mockito.when(reportRepository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(report));

    List<Entry> expected = Collections.singletonList(entry);
    Mockito.when(entryRepository.findByReportId(Mockito.anyLong()))
        .thenReturn(expected);
    List<Entry> actual = entryService.findByReportId(Mockito.anyLong());
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testFindByReportIdNull() {
    Mockito.when(reportRepository.findById(Mockito.anyLong())).thenReturn(null);
    List<Entry> result = entryService.findByReportId(Mockito.anyLong());
    Assert.assertNull(result);
  }

  @TestConfiguration
  static class EntryServiceTestContextConfiguration {

    @Bean
    public IEntryService entryService() {
      return new EntryService();
    }
  }
}
