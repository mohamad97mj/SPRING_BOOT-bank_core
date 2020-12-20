package ir.co.pna.exchange.service.publicOwner;

import ir.co.pna.exchange.dao.publicOwner.PublicOwnerDAO;
import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.ExternalTransaction;
import ir.co.pna.exchange.entity.PublicOwner;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class PublicOwnerServiceImpl implements PublicOwnerService {

	private PublicOwnerDAO publicOwnerDAO;
	
	@Autowired
	public PublicOwnerServiceImpl(@Qualifier("publicOwnerDAOHibernateImpl") PublicOwnerDAO thePublicOwnerDAO) {
		publicOwnerDAO = thePublicOwnerDAO;
	}
	
	@Override
	@Transactional
	public List<PublicOwner> findAll() {
		return publicOwnerDAO.findAll();
	}

	@Override
	@Transactional
	public PublicOwner findById(String ownerId) {

		PublicOwner theOwner = publicOwnerDAO.findById(ownerId);
		if (theOwner == null) {
			throw new MyEntityNotFoundException("owner id not found - " + ownerId);
		}

		return theOwner;
	}



	@Override
	@Transactional
	public String save(PublicOwner publicOwner) {
		return publicOwnerDAO.save(publicOwner);
	}

	@Override
	@Transactional
	public List<ExternalTransaction> getInExternalTransactions(String ownerId) {

		PublicOwner theOwner = publicOwnerDAO.findById(ownerId);
		if (theOwner == null) {
			throw new MyEntityNotFoundException("owner id not found - " + ownerId);
		}

		return theOwner.getInExternalTransactions();
	}

	@Override
	@Transactional
	public List<ExternalTransaction> getOutExternalTransactions(String ownerId) {

		PublicOwner theOwner = publicOwnerDAO.findById(ownerId);
		if (theOwner == null) {
			throw new MyEntityNotFoundException("owner id not found - " + ownerId);
		}

		return theOwner.getOutExternalTransactions();
	}

}






