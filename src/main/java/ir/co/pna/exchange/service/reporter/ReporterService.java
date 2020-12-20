package ir.co.pna.exchange.service.reporter;

import ir.co.pna.exchange.entity.Judge;
import ir.co.pna.exchange.entity.NormalContract;
import ir.co.pna.exchange.entity.Reporter;
import ir.co.pna.exchange.entity.Subcontract;

import java.util.List;


public interface ReporterService {

	List<Reporter> findAll();

	Reporter findById(String reporterId);

	String save(Reporter reporter);
}
