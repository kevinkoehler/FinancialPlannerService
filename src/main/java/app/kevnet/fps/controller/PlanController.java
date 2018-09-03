package app.kevnet.fps.controller;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.ErrorDetails;
import app.kevnet.fps.bean.Plan;
import app.kevnet.fps.service.IEntryService;
import app.kevnet.fps.service.IPlanService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@CrossOrigin
@RestController
@RequestMapping("/plan")
public class PlanController {

  @Autowired
  private IPlanService planService;

  @Autowired
  private IEntryService entryService;

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex,
      WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @GetMapping
  public ResponseEntity<List<Plan>> getAllPlans() {
    List<Plan> plans = planService.findAll();
    if (plans.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(plans, HttpStatus.OK);
  }

  @GetMapping(value = "/{id}")
  public ResponseEntity<Plan> getPlanById(@PathVariable long id) {
    Plan plan = planService.findById(id);
    if (plan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(plan, HttpStatus.OK);
  }

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
  public ResponseEntity<Plan> savePlan(@RequestBody Plan plan) {
    Plan savedPlan = planService.save(plan);
    if (savedPlan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(savedPlan, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Plan> deletePlan(@PathVariable long id) {
    Plan plan = planService.findById(id);
    if (plan == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    entryService.deleteByPlanId(id);
    planService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = "/{id}/entries")
  public ResponseEntity<List<Entry>> getEntriesByPlanId(
      @PathVariable long id) {
    List<Entry> entries = entryService.findByPlanId(id);
    if (entries == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if (entries.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(entries, HttpStatus.OK);
  }

}
