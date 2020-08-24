package ir.co.pna.exchange.service.Contract;

import ir.co.pna.exchange.dao.Contract.ContractDAO;
import ir.co.pna.exchange.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ContractServiceImpl implements ContractService {

	private ContractDAO contractDAO;
	
	@Autowired
	public ContractServiceImpl(@Qualifier("contractDAOJpaImpl") ContractDAO theContractDAO) {
		contractDAO = theContractDAO;
	}
	
	@Override
	@Transactional
	public List<Contract> findAll() {
		return contractDAO.findAll();
	}

	@Override
	@Transactional
	public Contract findById(int theId) {
		return contractDAO.findById(theId);
	}

	@Override
	@Transactional
	public long save(Contract Contract) {
		return contractDAO.save(Contract);
	}

	@Override
	@Transactional
	public void deleteById(int theId) {
		contractDAO.deleteById(theId);
	}

}






