package app.kevnet.fps.service;

import app.kevnet.fps.bean.Plan;
import java.util.List;

public interface IPlanService {

  List<Plan> findAll();

  Plan findById(long id);

  Plan save(Plan plan);

  void deleteById(long id);
}
