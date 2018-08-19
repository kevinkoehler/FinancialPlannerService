package app.kevnet.fps.service;

import app.kevnet.fps.bean.Entry;
import java.util.List;

public interface IEntryService {

  Entry findById(long id);

  Entry save(Entry entry);

  void deleteById(long id);

  List<Entry> findByReportId(long reportId);

}
