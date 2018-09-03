package app.kevnet.fps.service;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.Plan;
import app.kevnet.fps.repository.EntryRepository;
import app.kevnet.fps.repository.PlanRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntryService implements IEntryService {

  @Autowired
  PlanRepository planRepository;
  @Autowired
  private EntryRepository entryRepository;

  @Override
  public Entry findById(long id) {
    Optional<Entry> entry = entryRepository.findById(id);
    if (entry != null && entry.isPresent()) {
      return entry.get();
    }
    return null;
  }

  @Override
  public Entry save(Entry entry) {
    return this.entryRepository.save(entry);
  }

  @Override
  public void deleteById(long id) {
    entryRepository.deleteById(id);
  }

  @Override
  public List<Entry> findByPlanId(long planId) {
    Optional<Plan> plan = planRepository.findById(planId);
    if (plan != null && plan.isPresent()) {
      return entryRepository.findByPlanIdOrderByTypeDescNameAsc(planId);
    }
    return null;
  }

  @Override
  public void deleteByPlanId(long planId) {
    entryRepository.deleteByPlanId(planId);
  }

}
