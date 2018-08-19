package app.kevnet.fps.service;

import app.kevnet.fps.bean.Report;
import app.kevnet.fps.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService implements IReportService {

  @Autowired
  private ReportRepository reportRepository;

  @Override
  public List<Report> findAll() {
    return (List<Report>) reportRepository.findAll();
  }

  @Override
  public Report findById(long id) {
    Optional<Report> report = reportRepository.findById(id);
    if (report != null && report.isPresent()) {
      return report.get();
    }
    return null;
  }

  @Override
  public Report save(Report report) {
    return reportRepository.save(report);
  }

  @Override
  public void deleteById(long id) {
    reportRepository.deleteById(id);
  }

}
