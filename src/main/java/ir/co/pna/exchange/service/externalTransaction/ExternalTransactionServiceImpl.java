package ir.co.pna.exchange.service.externalTransaction;

import ir.co.pna.exchange.dao.externalTransaction.ExternalTransactionDAO;
import ir.co.pna.exchange.entity.ExternalTransaction;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
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

		ExternalTransaction theExternalTransaction = externalTransactionDAO.findById(theId);

		if (theExternalTransaction == null) {
			throw new MyEntityNotFoundException("ExternalTransaction id not found - " + theId);
		}
		return theExternalTransaction;
	}

	@Override
	@Transactional
	public int save(ExternalTransaction ExternalTransaction) {
		return externalTransactionDAO.save(ExternalTransaction);
	}

}






