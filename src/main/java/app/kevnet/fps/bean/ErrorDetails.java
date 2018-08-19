package app.kevnet.fps.bean;

import java.util.Date;

public class ErrorDetails {
  private Date timestamp;
  private String details;

  public ErrorDetails(Date timestamp, String details) {
    this.timestamp = timestamp;
    this.details = details;
  }

  public ErrorDetails() {
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ErrorDetails)) {
      return false;
    }

    ErrorDetails that = (ErrorDetails) o;

    if (timestamp != null ? !timestamp.equals(that.timestamp)
        : that.timestamp != null) {
      return false;
    }
    return details != null ? details.equals(that.details)
        : that.details == null;
  }

  @Override
  public int hashCode() {
    int result = timestamp != null ? timestamp.hashCode() : 0;
    result = 31 * result + (details != null ? details.hashCode() : 0);
    return result;
  }
}
