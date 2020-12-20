package ir.co.pna.exchange.service.operationalOwner;

import ir.co.pna.exchange.dao.oprationalOwner.OperationalOwnerDAO;
import ir.co.pna.exchange.dao.publicOwner.PublicOwnerDAO;
import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.ExternalTransaction;
import ir.co.pna.exchange.entity.OperationalOwner;
import ir.co.pna.exchange.entity.PublicOwner;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OperationalOwnerServiceImpl implements OperationalOwnerService {

    private OperationalOwnerDAO operationalOwnerDAO;

    @Autowired
    public OperationalOwnerServiceImpl(@Qualifier("operationalOwnerDAOHibernateImpl") OperationalOwnerDAO theOperationalOwnerDAO) {
        operationalOwnerDAO = theOperationalOwnerDAO;
    }

    @Override
    @Transactional
    public List<OperationalOwner> findAll() {
        return operationalOwnerDAO.findAll();
    }

    @Override
    @Transactional
    public OperationalOwner findByType(OwnerType ownerType) {

        OperationalOwner theOwner = operationalOwnerDAO.findByType(ownerType);
        if (theOwner == null) {
            throw new MyEntityNotFoundException("owner not found - " + ownerType);
        }

        return theOwner;
    }


    @Override
    @Transactional
    public String save(OperationalOwner operationalOwner) {
        return operationalOwnerDAO.save(operationalOwner);
    }

    @Override
    @Transactional
    public List<ExternalTransaction> getInExternalTransactions(OwnerType ownerType) {

        OperationalOwner theOwner = operationalOwnerDAO.findByType(ownerType);
        if (theOwner == null) {
            throw new MyEntityNotFoundException("owner not found - " + ownerType);
        }

        return theOwner.getInExternalTransactions();
    }


    @Override
    @Transactional
    public List<ExternalTransaction> getOutExternalTransactions(OwnerType ownerType) {

        OperationalOwner theOwner = operationalOwnerDAO.findByType(ownerType);
        if (theOwner == null) {
            throw new MyEntityNotFoundException("owner not found - " + ownerType);
        }

        return theOwner.getOutExternalTransactions();
    }

    @Override
    @Transactional
    public List<ExternalTransaction> getInExternalTransactionsTimeInterval(OwnerType ownerType, long from, long to) {

        OperationalOwner theOwner = operationalOwnerDAO.findByType(ownerType);
        if (theOwner == null) {
            throw new MyEntityNotFoundException("owner not found - " + ownerType);
        }
        List<ExternalTransaction> temp = theOwner.getInExternalTransactions().stream().filter(t -> t.getDate() > from && t.getDate() < to).collect(Collectors.toList());
        return theOwner.getInExternalTransactions().stream().filter(t -> t.getDate() > from && t.getDate() < to).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<ExternalTransaction> getOutExternalTransactionsTimeInterval(OwnerType ownerType, long from, long to) {

        OperationalOwner theOwner = operationalOwnerDAO.findByType(ownerType);
        if (theOwner == null) {
            throw new MyEntityNotFoundException("owner not found - " + ownerType);
        }

        List<ExternalTransaction> temp = theOwner.getOutExternalTransactions().stream().filter(t -> t.getDate() > from && t.getDate() < to).collect(Collectors.toList());
        return theOwner.getOutExternalTransactions().stream().filter(t -> t.getDate() > from && t.getDate() < to).collect(Collectors.toList());
    }

}






