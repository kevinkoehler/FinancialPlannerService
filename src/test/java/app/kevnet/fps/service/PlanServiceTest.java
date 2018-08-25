package app.kevnet.fps.service;

import app.kevnet.fps.bean.Plan;
import app.kevnet.fps.repository.PlanRepository;
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
public class PlanServiceTest {

  private Plan plan;
  @Autowired
  private PlanService service;
  @MockBean
  private PlanRepository repository;

  @Before
  public void setUp() {
    plan = TestUtil.getPlan();
  }

  @Test
  public void testFindAll() {
    List<Plan> expected = Collections.singletonList(plan);
    Mockito.when(repository.findAll()).thenReturn(expected);
    List<Plan> actual = service.findAll();
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testFindById() {
    Mockito.when(repository.findById(Mockito.anyLong()))
        .thenReturn(Optional.of(plan));
    Plan result = service.findById(Mockito.anyLong());
    Assert.assertEquals(plan, result);
  }

  @Test
  public void testFindByIdNull() {
    Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(null);
    Plan result = service.findById(Mockito.anyLong());
    Assert.assertNull(result);
  }

  @Test
  public void testSave() {
    Mockito.when(repository.save(plan)).thenReturn(plan);
    Plan result = service.save(plan);
    Assert.assertEquals(plan, result);
  }

  @Test
  public void testDeleteById() {
    service.deleteById(Mockito.anyLong());
  }

  @TestConfiguration
  static class PlanServiceTestContextConfiguration {

    @Bean
    public IPlanService planService() {
      return new PlanService();
    }
  }

}
