package app.kevnet.fps.bean;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "financial_planner", name = "plan")
public class Plan {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 100, nullable = false)
  private String name;

  @Column(name = "current_savings", nullable = false)
  private BigDecimal currentSavings;

  @Column(name = "goal_savings", nullable = false)
  private BigDecimal goalSavings;

  public Plan(Long id, String name, BigDecimal currentSavings,
      BigDecimal goalSavings) {
    this.id = id;
    this.name = name;
    this.currentSavings = currentSavings;
    this.goalSavings = goalSavings;
  }

  public Plan() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getCurrentSavings() {
    return currentSavings;
  }

  public void setCurrentSavings(BigDecimal currentSavings) {
    this.currentSavings = currentSavings;
  }

  public BigDecimal getGoalSavings() {
    return goalSavings;
  }

  public void setGoalSavings(BigDecimal goalSavings) {
    this.goalSavings = goalSavings;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Plan)) {
      return false;
    }

    Plan plan = (Plan) o;

    if (id != null ? !id.equals(plan.id) : plan.id != null) {
      return false;
    }
    if (name != null ? !name.equals(plan.name) : plan.name != null) {
      return false;
    }
    if (currentSavings != null ? !currentSavings.equals(plan.currentSavings)
        : plan.currentSavings != null) {
      return false;
    }
    return goalSavings != null ? goalSavings.equals(plan.goalSavings)
        : plan.goalSavings == null;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result =
        31 * result + (currentSavings != null ? currentSavings.hashCode() : 0);
    result = 31 * result + (goalSavings != null ? goalSavings.hashCode() : 0);
    return result;
  }
}
