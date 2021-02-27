package ir.co.pna.exchange.service.operationalOwner;

import com.sun.xml.messaging.saaj.util.ByteOutputStream;
import ir.co.pna.exchange.dao.normalContract.NormalContractDAO;
import ir.co.pna.exchange.dao.oprationalOwner.OperationalOwnerDAO;
import ir.co.pna.exchange.emum.ContractStatus;
import ir.co.pna.exchange.emum.JudgeVote;
import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.*;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.utility.GlobalVariables;
import ir.co.pna.exchange.utility.XLSX;
//import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class OperationalOwnerServiceImpl implements OperationalOwnerService {

    private OperationalOwnerDAO operationalOwnerDAO;
    private NormalContractDAO normalContractDAO;

    @Autowired
    public OperationalOwnerServiceImpl(@Qualifier("operationalOwnerDAOHibernateImpl") OperationalOwnerDAO theOperationalOwnerDAO,
                                       @Qualifier("normalContractDAOJpaImpl") NormalContractDAO theNormalContractDAO) {
        operationalOwnerDAO = theOperationalOwnerDAO;
        normalContractDAO = theNormalContractDAO;
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

//    1613761098754
//      1300653000

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

    @Override
    @Transactional
    public byte[] getSystemOutput(long from, long to) throws IOException {
        ArrayList<ArrayList<Object>> systemOutput = new ArrayList<>();
        ArrayList<Object> headers = new ArrayList<>();
        headers.add("شماره حساب مبداء");
        headers.add("شماره حساب مقصد");
        headers.add("مقدار");
        systemOutput.add(headers);
        List<NormalContract> normalContracts = normalContractDAO.findAll();
        int normalContractsSize = normalContracts.size();
        ArrayList<Object> rowObj = null;
        String src = null;
        String dst = null;
        long amount = 0;
        for (int i = 0; i < normalContractsSize; i++) {
            NormalContract normalContract = normalContracts.get(i);
            if (normalContract.isClosed() && normalContract.getCompletionDate() > from && normalContract.getCompletionDate() < to) {
                rowObj = new ArrayList<>();
                if (normalContract.getReturnAccount().getCredit() > 0) {
                    src = GlobalVariables.operationalReturnOwner.getBankAccountId();
                    dst = normalContract.getSrcPublicOwner().getBankAccountId();
                    amount = normalContract.getReturnAccount().getCredit();
                    rowObj.add(src);
                    rowObj.add(dst);
                    rowObj.add(amount);
                    systemOutput.add(rowObj);
                }
                List<Subcontract> subcontracts = normalContract.getSubcontracts();
                int subcontractsSize = subcontracts.size();
                for (int j = 0; j < subcontractsSize; j++) {
                    rowObj = new ArrayList<>();
                    Subcontract subcontract = subcontracts.get(j);
                    if (subcontract.isClosed() && subcontract.getCompletionDate() > from && subcontract.getCompletionDate() < to) {
                        if (subcontract.getStatus() == ContractStatus.CONFIRMED_BY_IMPORTER) {
                            src = GlobalVariables.operationalExporterOwner.getBankAccountId();
                            dst = subcontract.getDstPublicOwner().getBankAccountId();
                            amount = subcontract.getExporterAccount().getCredit();
                        } else if (subcontract.getStatus() == ContractStatus.JUDGED) {
                            if (subcontract.getJudgeVote() == JudgeVote.DONE) {
                                src = GlobalVariables.operationalExporterOwner.getBankAccountId();
                                dst = subcontract.getDstPublicOwner().getBankAccountId();
                                amount = subcontract.getExporterAccount().getCredit();
                            } else {
                                src = GlobalVariables.operationalReturnOwner.getBankAccountId();
                                dst = subcontract.getParent().getSrcPublicOwner().getBankAccountId();
                                amount = subcontract.getReturnAccount().getCredit();
                            }
                        }
                        rowObj.add(src);
                        rowObj.add(dst);
                        rowObj.add(amount);
                        systemOutput.add(rowObj);
                    }
                }
            }
        }
        ByteArrayOutputStream baos = XLSX.writeFile("/home/mohamad/Documents/test-core/SPRING_BOOT-bank_core/output/test.xlsx", systemOutput);
        return baos.toByteArray();
    }
}






