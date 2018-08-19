package app.kevnet.fps.service;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.Report;
import app.kevnet.fps.repository.EntryRepository;
import app.kevnet.fps.repository.ReportRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntryService implements IEntryService {

  @Autowired
  private EntryRepository entryRepository;

  @Autowired
  ReportRepository reportRepository;

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
  public List<Entry> findByReportId(long reportId) {
    Optional<Report> report = reportRepository.findById(reportId);
    if (report != null && report.isPresent()) {
      return entryRepository.findByReportId(reportId);
    }
    return null;
  }

}
