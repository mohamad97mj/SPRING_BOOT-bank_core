package ir.co.pna.exchange.service.subcontract;

import ir.co.pna.exchange.client.sms.SmsClient;
import ir.co.pna.exchange.client.yaghut.YaghutClient;
import ir.co.pna.exchange.dao.account.AccountDAO;
import ir.co.pna.exchange.dao.judge.JudgeDAO;
import ir.co.pna.exchange.dao.normalContract.NormalContractDAO;
import ir.co.pna.exchange.dao.publicOwner.PublicOwnerDAO;
import ir.co.pna.exchange.dao.subcontract.SubcontractDAO;
import ir.co.pna.exchange.dao.transaction.TransactionDAO;
import ir.co.pna.exchange.dao.user.UserDAO;
import ir.co.pna.exchange.emum.AccountType;
import ir.co.pna.exchange.emum.ContractStatus;
import ir.co.pna.exchange.emum.JudgeVote;
import ir.co.pna.exchange.emum.TransactionOperatorType;
import ir.co.pna.exchange.entity.*;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static ir.co.pna.exchange.utility.GlobalConstant.operationalClaimOwner;
import static ir.co.pna.exchange.utility.GlobalConstant.operationalExporterOwner;


@Service
public class SubcontractServiceImpl implements SubcontractService {

    private UserDAO userDAO;
    private JudgeDAO judgeDAO;
    private SubcontractDAO subcontractDAO;
    private NormalContractDAO normalContractDAO;
    private PublicOwnerDAO publicOwnerDAO;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;
    private SmsClient smsClient;
    private YaghutClient yaghutClient;


    @Autowired
    public SubcontractServiceImpl(
            @Qualifier("userDAOHibernateImpl") UserDAO theUserDAO,
            @Qualifier("judgeDAOHibernateImpl") JudgeDAO theJudgeDAO,
            @Qualifier("subcontractDAOJpaImpl") SubcontractDAO theSubcontractDAO,
            @Qualifier("normalContractDAOJpaImpl") NormalContractDAO theNormalContractDAO,
            @Qualifier("publicOwnerDAOHibernateImpl") PublicOwnerDAO thePublicOwnerDAO,
            @Qualifier("accountDAOJpaImpl") AccountDAO theAccountDAO,
            @Qualifier("transactionDAOJpaImpl") TransactionDAO theTransactionDAO,
            SmsClient theSmsClient,
            YaghutClient theYaghutClient
    ) {
        userDAO = theUserDAO;
        judgeDAO = theJudgeDAO;
        subcontractDAO = theSubcontractDAO;
        normalContractDAO = theNormalContractDAO;
        publicOwnerDAO = thePublicOwnerDAO;
        accountDAO = theAccountDAO;
        transactionDAO = theTransactionDAO;
        smsClient = theSmsClient;
        yaghutClient = theYaghutClient;
    }

    @Override
    @Transactional
    public List<Subcontract> findAll() {
        return subcontractDAO.findAll();
    }

    @Override
    @Transactional
    public Subcontract findById(int theId) {
        Subcontract theSubcontract = subcontractDAO.findById(theId);

        if (theSubcontract == null) {
            throw new MyEntityNotFoundException("subcontract id not found - " + theId);
        }
        return theSubcontract;
    }

    @Override
    @Transactional
    public Subcontract save(Map<String, Object> payload) {
        int parentId = (int) payload.get("parent_id");
        NormalContract parent = normalContractDAO.findById(parentId);

        if (parent == null) {
            throw new MyEntityNotFoundException("parent id not found - " + parentId);
        }

        String dstOwnerBankAccountId = (String) payload.get("dst_owner_bank_account_id");
        PublicOwner dstPublicOwner = publicOwnerDAO.findById(dstOwnerBankAccountId);

        if (dstPublicOwner == null) {
            throw new MyEntityNotFoundException("dst owner id not found - " + dstOwnerBankAccountId);
        }

        long valueInRial = ((Number) payload.get("value_in_rial")).longValue();
        long remittanceValue = ((Number) payload.get("remittance_value")).longValue();
        long expireDate = ((Number) payload.get("expire_date")).longValue();
        String description = (String) payload.get("description");


        Subcontract theSubcontract = parent.createSubcontract(expireDate, dstPublicOwner, valueInRial, remittanceValue, description);

        Account claimAccount = new Account(AccountType.CLAIM, operationalClaimOwner, expireDate, theSubcontract);
        Account exporterAccount = new Account(AccountType.EXPORTER, operationalExporterOwner, expireDate, theSubcontract);


        theSubcontract.setClaimAccount(claimAccount);
        theSubcontract.setExporterAccount(exporterAccount);
        theSubcontract.setId(0);

        return subcontractDAO.save(theSubcontract);
    }

    @Override
    @Transactional
    public Subcontract update(Subcontract theSubcontract, Map<String, Object> payload) {

        ContractStatus status = ContractStatus.valueOf((String) payload.get("status"));
        theSubcontract.setStatus(status);
        return theSubcontract;
    }

    @Override
    @Transactional
    public Subcontract pay(Subcontract theSubcontract, Map<String, Object> payload) {
        String operatorNationalCode = (String) payload.get("operator_national_code");
        TransactionOperatorType operatorType = TransactionOperatorType.valueOf((String) payload.get("operator_type"));

        User operator = userDAO.findById(operatorNationalCode);
        if (operator == null) {
            throw new MyEntityNotFoundException("operator id not found - " + operatorNationalCode);
        }
        theSubcontract.pay(operatorType, operator, smsClient, yaghutClient);
        return subcontractDAO.save(theSubcontract);
    }

    @Override
    @Transactional
    public Subcontract judge(Subcontract theSubcontract, Map<String, Object> payload) {
        String operatorNationalId = (String) payload.get("operator_national_id");
        TransactionOperatorType operatorType = TransactionOperatorType.valueOf((String) payload.get("operator_type"));
        JudgeVote judgeVote = JudgeVote.valueOf((String) payload.get("judge_vote"));

        Judge operator = judgeDAO.findById(operatorNationalId);
        if (operator == null) {
            throw new MyEntityNotFoundException("operator id not found - " + operatorNationalId);
        }
        theSubcontract.judge(operatorType, operator, judgeVote, smsClient, yaghutClient);
        return subcontractDAO.save(theSubcontract);
    }


}






