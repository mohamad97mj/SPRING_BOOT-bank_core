package ir.co.pna.exchange.dao.user;

import ir.co.pna.exchange.entity.*;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


@Repository
public class UserDAOHibernateImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOHibernateImpl(EntityManager theEntityManager) {
        entityManager = theEntityManager;
    }

    @Override
    public List<User> findAll() {

        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery =
                currentSession.createQuery("from User", User.class);

        // execute query and get result list
        List<User> users = theQuery.getResultList();

        // return the results
        return users;
    }

    @Override
    public User findById(String userId) {

        Session currentSession = entityManager.unwrap(Session.class);

        User theUser =
                currentSession.get(User.class, userId);

        return theUser;
    }

    @Override
    public String save(User theUser) {

        Session currentSession = entityManager.unwrap(Session.class);
        // update with id from db ... so we can get generated id for save/insert
        currentSession.saveOrUpdate(theUser);

        return theUser.getId();

    }


    @Override
    public List<PublicOwner> getPublicOwners(String userId) {

        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery = currentSession.createQuery("SELECT o FROM PublicOwner o INNER JOIN o.users u WHERE u.id = :userId");
        theQuery.setParameter("userId", userId);
        // execute query and get result list
        List<PublicOwner> thePublicOwners = theQuery.getResultList();

        // return the results

        return thePublicOwners;
    }

    @Override
    public PublicOwner getPublicOwner(String userId, String ownerId) {
        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery = currentSession.createQuery("SELECT o FROM PublicOwner o INNER JOIN o.users u WHERE u.id = :userId AND o.bankAccountId = :ownerId");
        theQuery.setParameter("userId", userId);
        theQuery.setParameter("ownerId", ownerId);
        // execute query and get result list
        PublicOwner thePublicOwner = (PublicOwner) theQuery.getSingleResult();

        return thePublicOwner;
    }

    // this is for exchanger
    public List<NormalContract> getPublicOwnerInNormalContracts(String userId, String ownerId) {

        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery = currentSession.createQuery("SELECT c FROM NormalContract c INNER JOIN c.dstPublicOwner o INNER JOIN o.users u WHERE o.bankAccountId = :ownerId AND u.id = :userId");

        theQuery.setParameter("userId", userId);
        theQuery.setParameter("ownerId", ownerId);
        // execute query and get result list
        List<NormalContract> theNormalContracts = theQuery.getResultList();
        return theNormalContracts;

    }

    @Override
    public List<NormalContract> getPublicOwnerOutNormalContracts(String userId, String ownerId) {
        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery = currentSession.createQuery("SELECT c FROM NormalContract c INNER JOIN c.srcPublicOwner o INNER JOIN o.users u WHERE o.bankAccountId = :ownerId AND u.id = :userId");
        theQuery.setParameter("userId", userId);
        theQuery.setParameter("ownerId", ownerId);
        // execute query and get result list
        List<NormalContract> theNormalContracts = theQuery.getResultList();
        return theNormalContracts;
    }


    @Override
    public NormalContract getOwnerOutNormalContract(String userId, String ownerId, int normalContractId) {
        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery = currentSession.createQuery("SELECT c FROM NormalContract c INNER JOIN c.srcPublicOwner o INNER JOIN o.users u WHERE c.id = :normalContractId AND  o.bankAccountId = :ownerId AND u.id = :userId");
        theQuery.setParameter("userId", userId);
        theQuery.setParameter("ownerId", ownerId);
        theQuery.setParameter("normalContractId", normalContractId);
        // execute query and get result list
        NormalContract theNormalContract = (NormalContract) theQuery.getSingleResult();

        return theNormalContract;
    }

    @Override
    public NormalContract getOwnerInNormalContract(String userId, String ownerId, int normalContractId) {
        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery = currentSession.createQuery("SELECT c FROM NormalContract c INNER JOIN c.dstPublicOwner o INNER JOIN o.users u WHERE c.id = :normalContractId AND  o.bankAccountId = :ownerId AND u.id = :userId");
        theQuery.setParameter("userId", userId);
        theQuery.setParameter("ownerId", ownerId);
        theQuery.setParameter("normalContractId", normalContractId);
        // execute query and get result list
        NormalContract theNormalContract = (NormalContract) theQuery.getSingleResult();

        return theNormalContract;
    }

    @Override
    public List<Subcontract> getPublicOwnerInSubcontracts(String userId, String ownerId) {
        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery = currentSession.createQuery("SELECT c FROM Subcontract c INNER JOIN c.dstPublicOwner o INNER JOIN o.users u WHERE o.bankAccountId = :ownerId AND u.id = :userId");
        theQuery.setParameter("userId", userId);
        theQuery.setParameter("ownerId", ownerId);
        // execute query and get result list
        List<Subcontract> theSubcontracts = theQuery.getResultList();

        return theSubcontracts;
    }


    @Override
    public Subcontract getOwnerInSubcontract(String userId, String ownerId, int subcontractId) {
        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery = currentSession.createQuery("SELECT c FROM Subcontract c INNER JOIN c.dstPublicOwner o INNER JOIN o.users u WHERE c.id = :subcontractId AND o.bankAccountId = :ownerId AND u.id = :userId");
        theQuery.setParameter("userId", userId);
        theQuery.setParameter("ownerId", ownerId);
        theQuery.setParameter("subcontractId", subcontractId);
        // execute query and get result list
        Subcontract theSubcontract = (Subcontract) theQuery.getSingleResult();

        return theSubcontract;
    }

    @Override
    public List<Subcontract> getPublicOwnerInNormalContractSubcontracts(String userId, String ownerId, int normalContractId) {
        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery = currentSession.createQuery("SELECT s FROM Subcontract s INNER JOIN s.parent c INNER JOIN c.dstPublicOwner o INNER JOIN o.users u WHERE c.id = :normalContractId AND o.bankAccountId = :ownerId AND u.id = :userId");
        theQuery.setParameter("userId", userId);
        theQuery.setParameter("ownerId", ownerId);
        theQuery.setParameter("normalContractId", normalContractId);
        // execute query and get result list
        List<Subcontract> theSubcontracts = theQuery.getResultList();

        return theSubcontracts;
    }

    @Override
    public Subcontract getPublicOwnerInNormalContractSubcontract(String userId, String ownerId, int normalContractId, int subcontractId) {
        Session currentSession = entityManager.unwrap(Session.class);
        // create a query
        Query theQuery = currentSession.createQuery("SELECT s FROM Subcontract s INNER JOIN s.parent c INNER JOIN c.dstPublicOwner o INNER JOIN o.users u WHERE s.id = :subcontractId AND c.id = :normalContractId AND o.bankAccountId = :ownerId AND u.id = :userId");
        theQuery.setParameter("userId", userId);
        theQuery.setParameter("ownerId", ownerId);
        theQuery.setParameter("normalContractId", normalContractId);
        theQuery.setParameter("subcontractId", subcontractId);
        // execute query and get result list
        Subcontract theSubcontract = (Subcontract) theQuery.getSingleResult();

        return theSubcontract;

    }
}










