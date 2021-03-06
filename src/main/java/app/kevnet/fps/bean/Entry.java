package app.kevnet.fps.bean;

import app.kevnet.fps.enums.Frequency;
import app.kevnet.fps.enums.Type;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(schema = "financial_planner", name = "entry")
public class Entry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "plan_id", nullable = false, updatable = false)
  private Long planId;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "amount", nullable = false)
  private BigDecimal amount;

  @Column(name = "type", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Type type;

  @Column(name = "frequency", nullable = false)
  @Enumerated(value = EnumType.STRING)
  private Frequency frequency;

  public Entry(Long id, Long planId, String name, BigDecimal amount,
      Type type, Frequency frequency) {
    this.id = id;
    this.planId = planId;
    this.name = name;
    this.amount = amount;
    this.type = type;
    this.frequency = frequency;
  }

  public Entry() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPlanId() {
    return planId;
  }

  public void setPlanId(Long planId) {
    this.planId = planId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public Frequency getFrequency() {
    return frequency;
  }

  public void setFrequency(Frequency frequency) {
    this.frequency = frequency;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Entry)) {
      return false;
    }

    Entry entry = (Entry) o;

    if (id != null ? !id.equals(entry.id) : entry.id != null) {
      return false;
    }
    if (planId != null ? !planId.equals(entry.planId)
        : entry.planId != null) {
      return false;
    }
    if (name != null ? !name.equals(entry.name) : entry.name != null) {
      return false;
    }
    if (amount != null ? !amount.equals(entry.amount) : entry.amount != null) {
      return false;
    }
    if (type != entry.type) {
      return false;
    }
    return frequency == entry.frequency;
  }

  @Override
  public int hashCode() {
    int result = id != null ? id.hashCode() : 0;
    result = 31 * result + (planId != null ? planId.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (amount != null ? amount.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (frequency != null ? frequency.hashCode() : 0);
    return result;
  }
}
