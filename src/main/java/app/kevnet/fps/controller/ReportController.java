package app.kevnet.fps.controller;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.ErrorDetails;
import app.kevnet.fps.bean.Report;
import app.kevnet.fps.service.IEntryService;
import app.kevnet.fps.service.IReportService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@RestController
@RequestMapping("/report")
public class ReportController {

  @Autowired
  private IReportService reportService;

  @Autowired
  private IEntryService entryService;

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex,
      WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @GetMapping
  public ResponseEntity<List<Report>> getAllReports() {
    List<Report> reports = reportService.findAll();
    if (reports.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(reports, HttpStatus.OK);
  }

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
  public ResponseEntity<Report> saveReport(@RequestBody Report report) {
    Report savedReport = reportService.save(report);
    if (savedReport == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(savedReport, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Report> deleteReport(@PathVariable long id) {
    Report report = reportService.findById(id);
    if (report == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    reportService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping(value = "/{id}/entries")
  public ResponseEntity<List<Entry>> getEntriesByReportId(
      @PathVariable long id) {
    List<Entry> entries = entryService.findByReportId(id);
    if (entries == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else if (entries.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(entries, HttpStatus.OK);
  }

}
