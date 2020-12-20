package ir.co.pna.exchange.service.user;

import ir.co.pna.exchange.dao.normalContract.NormalContractDAO;
import ir.co.pna.exchange.dao.publicOwner.PublicOwnerDAO;
import ir.co.pna.exchange.dao.subcontract.SubcontractDAO;
import ir.co.pna.exchange.dao.user.UserDAO;
import ir.co.pna.exchange.entity.*;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private PublicOwnerDAO publicOwnerDAO;
    private NormalContractDAO normalContractDAO;
    private SubcontractDAO subcontractDAO;

    @Autowired
    public UserServiceImpl(
            @Qualifier("userDAOHibernateImpl") UserDAO theUserDAO,
            @Qualifier("publicOwnerDAOHibernateImpl") PublicOwnerDAO thePublicOwnerDAO,
            @Qualifier("normalContractDAOJpaImpl") NormalContractDAO theNormalContractDAO,
            @Qualifier("subcontractDAOJpaImpl") SubcontractDAO theSubcontractDAO
    ) {
        userDAO = theUserDAO;
        publicOwnerDAO = thePublicOwnerDAO;
        normalContractDAO = theNormalContractDAO;
        subcontractDAO = theSubcontractDAO;
    }

    @Override
    @Transactional
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    @Transactional
    public User findById(String userId) {
        User theUser = userDAO.findById(userId);
        if (theUser == null) {
            throw new MyEntityNotFoundException("User id not found - " + userId);
        }
        return theUser;
    }

    @Override
    @Transactional
    public String save(User User) {
        return userDAO.save(User);
    }


    @Override
    @Transactional
    public List<PublicOwner> getPublicOwners(String userId) {
        return userDAO.getPublicOwners(userId);
    }

    @Override
    @Transactional
    public PublicOwner getPublicOwner(String userId, String ownerId) {

        PublicOwner thePublicOwner = userDAO.getPublicOwner(userId, ownerId);
        if (thePublicOwner == null) {
            throw new MyEntityNotFoundException("owner id not found - " + ownerId);
        }
        return thePublicOwner;
    }


    @Override
    @Transactional
    public List<NormalContract> getPublicOwnerInNormalContracts(String userId, String ownerId) {
        return userDAO.getPublicOwnerInNormalContracts(userId, ownerId);
    }


    @Override
    @Transactional
    public NormalContract getPublicOwnerInNormalContract(String userId, String ownerId, int normalContractId) {

        NormalContract theNormalContract = userDAO.getOwnerInNormalContract(userId, ownerId, normalContractId);
        if (theNormalContract == null) {
            throw new MyEntityNotFoundException("in normal contract id not found - " + normalContractId);
        }
        return theNormalContract;
    }

    @Override
    @Transactional
    public List<NormalContract> getPublicOwnerOutNormalContracts(String userId, String ownerId) {
        return userDAO.getPublicOwnerOutNormalContracts(userId, ownerId);
    }

    @Override
    @Transactional
    public NormalContract getPublicOwnerOutNormalContract(String userId, String ownerId, int normalContractId) {
        NormalContract theNormalContract = userDAO.getOwnerOutNormalContract(userId, ownerId, normalContractId);
        if (theNormalContract == null) {
            throw new MyEntityNotFoundException("out normal contract id not found - " + normalContractId);
        }
        return theNormalContract;
    }

    @Override
    @Transactional
    public List<Subcontract> getPublicOwnerInSubcontracts(String userId, String ownerId) {
        return userDAO.getPublicOwnerInSubcontracts(userId, ownerId);
    }

    @Override
    @Transactional
    public Subcontract getPublicOwnerInSubcontract(String userId, String ownerId, int subcontractId) {

        Subcontract theSubcontract = userDAO.getOwnerInSubcontract(userId, ownerId, subcontractId);
        if (theSubcontract == null) {
            throw new MyEntityNotFoundException("in subcontract id not found - " + subcontractId);
        }
        return theSubcontract;
    }

    @Override
    @Transactional
    public List<Subcontract> getPublicOwnerInNormalContractSubcontracts(String userId, String ownerId, int normalContractId) {
        return userDAO.getPublicOwnerInNormalContractSubcontracts(userId, ownerId, normalContractId);
    }

    @Override
    @Transactional
    public Subcontract getPublicOwnerInNormalContractSubcontract(String userId, String ownerId, int normalContractId, int subcontractId) {

        Subcontract theSubcontract = userDAO.getPublicOwnerInNormalContractSubcontract(userId, ownerId, normalContractId, subcontractId);
        if (theSubcontract == null) {
            throw new MyEntityNotFoundException("out normal contract in subcontract id not found - " + subcontractId);
        }
        return theSubcontract;
    }
}






