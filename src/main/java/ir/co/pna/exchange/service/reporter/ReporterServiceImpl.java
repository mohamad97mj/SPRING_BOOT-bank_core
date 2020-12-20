package ir.co.pna.exchange.service.reporter;

import ir.co.pna.exchange.dao.judge.JudgeDAO;
import ir.co.pna.exchange.dao.reporter.ReporterDAO;
import ir.co.pna.exchange.entity.Judge;
import ir.co.pna.exchange.entity.Reporter;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ReporterServiceImpl implements ReporterService {

    private ReporterDAO reporterDAO;

    @Autowired
    public ReporterServiceImpl(@Qualifier("reporterDAOHibernateImpl") ReporterDAO theReporterDAO) {
        reporterDAO = theReporterDAO;
    }

    @Override
    @Transactional
    public List<Reporter> findAll() {
        return reporterDAO.findAll();
    }

    @Override
    @Transactional
    public Reporter findById(String judgeId) {
        Reporter theReporter = reporterDAO.findById(judgeId);

        if (theReporter == null) {
            throw new MyEntityNotFoundException("Reporter id not found - " + judgeId);
        }
        return theReporter;
    }

    @Override
    @Transactional
    public String save(Reporter reporter) {
        return reporterDAO.save(reporter);
    }


}






