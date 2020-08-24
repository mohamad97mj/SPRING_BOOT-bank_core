package ir.co.pna.exchange.service.Judge;

import ir.co.pna.exchange.entity.ExternalTransaction;
import ir.co.pna.exchange.entity.Judge;

import java.util.List;


public interface JudgeService {

	public List<Judge> findAll();

	public Judge findById(long id);

	public long save(Judge judge);

	public void deleteById(long id);

}
