package app.kevnet.fps.service;

import app.kevnet.fps.bean.Report;
import app.kevnet.fps.repository.ReportRepository;
import app.kevnet.fps.util.TestUtil;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang3.RandomUtils;
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
public class ReportServiceTest {

  @TestConfiguration
  static class ReportServiceTestContextConfiguration {

    @Bean
    public IReportService reportService() {
      return new ReportService();
    }
  }

  private Report report;

  @Autowired
  private ReportService service;

  @MockBean
  private ReportRepository repository;

  @Before
  public void setUp() {
    report = TestUtil.getReport();
  }

  @Test
  public void testFindAll() {
    List<Report> expected = Collections.singletonList(report);
    Mockito.when(repository.findAll()).thenReturn(expected);
    List<Report> actual = service.findAll();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testFindById() {
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(report));
    Report result = service.findById(Mockito.anyLong());
    Assert.assertEquals(report, result);
  }

  @Test
  public void testFindByIdNull() {
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(null);
    Report result = service.findById(Mockito.anyLong());
    Assert.assertNull(result);
  }

  @Test
  public void testSave() {
    Mockito.when(repository.save(report)).thenReturn(report);
    Report result = service.save(report);
    Assert.assertEquals(report, result);
  }

  @Test
  public void testDeleteById() {
    service.deleteById(Mockito.anyLong());
  }

}
