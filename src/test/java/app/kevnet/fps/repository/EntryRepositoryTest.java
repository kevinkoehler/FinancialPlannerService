package app.kevnet.fps.repository;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.Report;
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
  private ReportRepository reportRepository;

  @Autowired
  private EntryRepository entryRepository;

  @Test
  public void testFindByReportId() {
    Report report = TestUtil.getReport();
    Report savedReport = entityManager.persist(report);
    Assert.assertNotNull(report);
    Long reportId = savedReport.getId();
    Assert.assertTrue(reportId != null && reportId > 0L);

    Entry entry = TestUtil.getEntry();
    entry.setReportId(reportId);
    Entry savedEntry = entityManager.persist(entry);
    Assert.assertNotNull(savedEntry);
    Long entryId = savedEntry.getId();
    Assert.assertTrue(entryId != null && entryId > 0L);

    List<Entry> retrievedEntries = entryRepository.findByReportId(reportId);
    Assert.assertTrue(retrievedEntries != null && !retrievedEntries.isEmpty());
    Assert.assertEquals(1, retrievedEntries.size());
    Assert.assertEquals(savedEntry, retrievedEntries.get(0));
  }
}
