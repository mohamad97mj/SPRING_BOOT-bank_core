package ir.co.pna.exchange.service.Owner;

import ir.co.pna.exchange.dao.Owner.OwnerDAO;
import ir.co.pna.exchange.entity.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class OwnerServiceImpl implements OwnerService {

	private OwnerDAO ownerDAO;
	
	@Autowired
	public OwnerServiceImpl(@Qualifier("ownerDAOJpaImpl") OwnerDAO theOwnerDAO) {
		ownerDAO = theOwnerDAO;
	}
	
	@Override
	@Transactional
	public List<Owner> findAll() {
		return ownerDAO.findAll();
	}

	@Override
	@Transactional
	public Owner findById(long theId) {
		return ownerDAO.findById(theId);
	}

	@Override
	@Transactional
	public long save(Owner Owner) {
		return ownerDAO.save(Owner);
	}

	@Override
	@Transactional
	public void deleteById(long theId) {
		ownerDAO.deleteById(theId);
	}

}






