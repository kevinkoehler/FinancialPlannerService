package app.kevnet.fps.repository;

import app.kevnet.fps.bean.Entry;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {

  List<Entry> findByPlanIdOrderByTypeDescNameAsc(long planId);

  @Transactional
  void deleteByPlanId(long planId);
}
