package app.kevnet.fps.controller;

import app.kevnet.fps.bean.Entry;
import app.kevnet.fps.bean.ErrorDetails;
import app.kevnet.fps.service.IEntryService;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

@CrossOrigin
@RestController
@RequestMapping("/entry")
public class EntryController {

  @Autowired
  private IEntryService entryService;

  @ExceptionHandler(Exception.class)
  public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex,
      WebRequest request) {
    ErrorDetails errorDetails = new ErrorDetails(new Date(), ex.getMessage(),
        request.getDescription(false));
    return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
  public ResponseEntity<Entry> saveEntry(@RequestBody Entry entry) {
    Entry savedEntry = entryService.save(entry);
    if (savedEntry == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(savedEntry, HttpStatus.OK);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Entry> deleteEntry(@PathVariable long id) {
    Entry entry = entryService.findById(id);
    if (entry == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    entryService.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }


}
