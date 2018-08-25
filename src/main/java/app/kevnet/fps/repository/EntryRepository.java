package app.kevnet.fps.repository;

import app.kevnet.fps.bean.Entry;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EntryRepository extends JpaRepository<Entry, Long> {

  List<Entry> findByPlanId(long planId);

}
