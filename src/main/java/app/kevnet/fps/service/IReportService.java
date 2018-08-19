package app.kevnet.fps.service;

import app.kevnet.fps.bean.Report;
import java.util.List;

public interface IReportService {

  List<Report> findAll();

  Report findById(long id);

  Report save(Report report);

  void deleteById(long id);
}
