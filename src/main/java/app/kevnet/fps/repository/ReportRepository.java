package app.kevnet.fps.repository;

import app.kevnet.fps.bean.Report;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<Report, Long> {

}
