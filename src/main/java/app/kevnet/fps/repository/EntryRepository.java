package app.kevnet.fps.repository;

import app.kevnet.fps.bean.Entry;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface EntryRepository extends CrudRepository<Entry, Long> {

  List<Entry> findByReportId(long reportId);

}
