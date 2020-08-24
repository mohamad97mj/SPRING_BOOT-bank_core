package ir.co.pna.exchange.service.ExternalTransaction;

import ir.co.pna.exchange.dao.ExternalTransaction.ExternalTransactionDAO;
import ir.co.pna.exchange.entity.ExternalTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ExternalTransactionServiceImpl implements ExternalTransactionService {

	private ExternalTransactionDAO externalTransactionDAO;
	
	@Autowired
	public ExternalTransactionServiceImpl(@Qualifier("externalTransactionDAOJpaImpl") ExternalTransactionDAO theExternalTransactionDAO) {
		externalTransactionDAO = theExternalTransactionDAO;
	}
	
	@Override
	@Transactional
	public List<ExternalTransaction> findAll() {
		return externalTransactionDAO.findAll();
	}

	@Override
	@Transactional
	public ExternalTransaction findById(int theId) {
		return externalTransactionDAO.findById(theId);
	}

	@Override
	@Transactional
	public int save(ExternalTransaction ExternalTransaction) {
		return externalTransactionDAO.save(ExternalTransaction);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		externalTransactionDAO.deleteById(theId);
	}

}






