package app.kevnet.fps.repository;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.Plan;
import app.kevnet.fps.util.TestUtil;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class EntryRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private PlanRepository planRepository;

  @Autowired
  private EntryRepository entryRepository;

  @Test
  public void testFindByPlanId() {
    Plan plan = TestUtil.getPlan();
    Plan savedPlan = entityManager.persist(plan);
    Assert.assertNotNull(plan);
    Long planId = savedPlan.getId();
    Assert.assertTrue(planId != null && planId > 0L);

    Entry entry = TestUtil.getEntry();
    entry.setPlanId(planId);
    Entry savedEntry = entityManager.persist(entry);
    Assert.assertNotNull(savedEntry);
    Long entryId = savedEntry.getId();
    Assert.assertTrue(entryId != null && entryId > 0L);

    List<Entry> retrievedEntries = entryRepository.findByPlanId(planId);
    Assert.assertTrue(retrievedEntries != null && !retrievedEntries.isEmpty());
    Assert.assertEquals(1, retrievedEntries.size());
    Assert.assertEquals(savedEntry, retrievedEntries.get(0));
  }
}
