package app.kevnet.fps.service;

import app.kevnet.fps.bean.Plan;
import app.kevnet.fps.repository.PlanRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlanService implements IPlanService {

  @Autowired
  private PlanRepository planRepository;

  @Override
  public List<Plan> findAll() {
    return (List<Plan>) planRepository.findAll();
  }

  @Override
  public Plan findById(long id) {
    Optional<Plan> plan = planRepository.findById(id);
    if (plan != null && plan.isPresent()) {
      return plan.get();
    }
    return null;
  }

  @Override
  public Plan save(Plan plan) {
    return planRepository.save(plan);
  }

  @Override
  public void deleteById(long id) {
    planRepository.deleteById(id);
  }

}
