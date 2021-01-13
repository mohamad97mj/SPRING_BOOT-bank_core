package ir.co.pna.exchange.service.normalContract;

import ir.co.pna.exchange.client.sms.SmsClient;
import ir.co.pna.exchange.client.sms.generated_resources.SMSGateway;
import ir.co.pna.exchange.client.sms.generated_resources.SendSMSResponse;
import ir.co.pna.exchange.dao.account.AccountDAO;
import ir.co.pna.exchange.dao.judge.JudgeDAO;
import ir.co.pna.exchange.dao.normalContract.NormalContractDAO;
import ir.co.pna.exchange.dao.publicOwner.PublicOwnerDAO;
import ir.co.pna.exchange.dao.transaction.TransactionDAO;
import ir.co.pna.exchange.dao.user.UserDAO;
import ir.co.pna.exchange.emum.*;
import ir.co.pna.exchange.entity.*;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import ir.co.pna.exchange.utility.GlobalConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static ir.co.pna.exchange.utility.GlobalConstant.operationalExchangerOwner;
import static ir.co.pna.exchange.utility.GlobalConstant.operationalReturnOwner;


@Service
public class NormalContractServiceImpl implements NormalContractService {

    private NormalContractDAO normalContractDAO;
    private JudgeDAO judgeDAO;
    private PublicOwnerDAO publicOwnerDAO;
    private AccountDAO accountDAO;
    private UserDAO userDAO;
    private TransactionDAO transactionDAO;
    private SmsClient smsClient;

    @Autowired
    public NormalContractServiceImpl(
            @Qualifier("normalContractDAOJpaImpl") NormalContractDAO theNormalContractDAO,
            @Qualifier("judgeDAOHibernateImpl") JudgeDAO theJudgeDAO,
            @Qualifier("publicOwnerDAOHibernateImpl") PublicOwnerDAO thePublicOwnerDAO,
            @Qualifier("accountDAOJpaImpl") AccountDAO theAccountDAO,
            @Qualifier("userDAOHibernateImpl") UserDAO theUserDAO,
            @Qualifier("transactionDAOJpaImpl") TransactionDAO theTransactionDAO,
            SmsClient theSmsClient

    ) {
        normalContractDAO = theNormalContractDAO;
        judgeDAO = theJudgeDAO;
        publicOwnerDAO = thePublicOwnerDAO;
        accountDAO = theAccountDAO;
        userDAO = theUserDAO;
        transactionDAO = theTransactionDAO;
        smsClient = theSmsClient;
    }

    @Override
    @Transactional
    public List<NormalContract> findAll() {
        return normalContractDAO.findAll();
    }

    @Override
    @Transactional
    public NormalContract findById(int theId) {
        NormalContract theNormalContract = normalContractDAO.findById(theId);

        if (theNormalContract == null) {
            throw new MyEntityNotFoundException("normal contract id not found - " + theId);
        }
        return theNormalContract;
    }

    @Override
    @Transactional
    public NormalContract save(Map<String, Object> payload) {

        String judgeNationalId = (String) payload.get("judge_national_id");
        Judge theJudge = judgeDAO.findById(judgeNationalId);

        String srcOwnerBankAccountId = (String) payload.get("src_owner_bank_account_id");
        String dstOwnerBankAccountId = (String) payload.get("dst_owner_bank_account_id");

        PublicOwner srcPublicOwner = publicOwnerDAO.findById(srcOwnerBankAccountId);
        PublicOwner dstPublicOwner = publicOwnerDAO.findById(dstOwnerBankAccountId);

        long valueInRial = ((Number) payload.get("value_in_rial")).longValue();
        Currency remittanceCurrency = Currency.valueOf((String) payload.get("remittance_currency"));
        long remittanceValue = ((Number) payload.get("remittance_value")).longValue();
        SettlementType settlementType = SettlementType.valueOf((String) payload.get("settlement_type"));
        long expireDate = ((Number) payload.get("expire_date")).longValue();
        String description = (String) payload.get("description");

        NormalContract theNormalContract = new NormalContract(
                settlementType,
                srcPublicOwner,
                dstPublicOwner,
                theJudge,
                expireDate,
                valueInRial,
                remittanceValue,
                remittanceCurrency,
                description
        );

//        TODO determine a valid expireDate for accounts
//        Calendar tmp = Calendar.getInstance();

        Account returnAccount = new Account(AccountType.RETURN, operationalReturnOwner, expireDate, theNormalContract);
        Account exchangerAccount = new Account(AccountType.EXCHANGER, operationalExchangerOwner, expireDate, theNormalContract);

        theNormalContract.setReturnAccount(returnAccount);
        theNormalContract.setExchangerAccount(exchangerAccount);
        theNormalContract.setId(0);

        return normalContractDAO.save(theNormalContract);
    }

    @Override
    @Transactional
    public NormalContract update(NormalContract theNormalContract, Map<String, Object> payload) {

        ContractStatus status = ContractStatus.valueOf((String) payload.get("status"));
        theNormalContract.setStatus(status);
        return theNormalContract;
    }



    @Override
    @Transactional
    public List<Subcontract> getSubcontracts(int id) {
//        TODO implement in dao layer
        NormalContract theNormalContract = normalContractDAO.findById(id);
        if (theNormalContract == null) {
            throw new MyEntityNotFoundException("normal contract id not found - " + id);
        }
        return theNormalContract.getSubcontracts();
    }

    @Override
    @Transactional
    public NormalContract charge(NormalContract theNormalContract, Map<String, Object> payload) {

        String operatorNationalCode = (String) payload.get("operator_national_code");
        TransactionOperatorType operatorType = TransactionOperatorType.valueOf((String) payload.get("operator_type"));
        User operator = userDAO.findById(operatorNationalCode);
        if (operator == null) {
            throw new MyEntityNotFoundException("operator id not found - " + operatorNationalCode);
        }

        boolean isDone = theNormalContract.charge(operator, operatorType);
        if (isDone) {
            String message = GlobalConstant.operationalExchangerOwner.getBankAccountId() + "واریز به حساب";
            SendSMSResponse smsResponse = smsClient.sendSms(GlobalConstant.operationalExchangerOwner.getMobileNumber(), message, SMSGateway.ADVERTISEMENT, "demo");
            System.out.println(smsResponse.toString());
            System.err.println(smsResponse.getSendSMSResult());

        }
        return normalContractDAO.save(theNormalContract);
    }


    @Override
    @Transactional
    public NormalContract claim(NormalContract theNormalContract, Map<String, Object> payload) {

        String operatorNationalCode = (String) payload.get("operator_national_code");
        TransactionOperatorType operatorType = TransactionOperatorType.valueOf((String) payload.get("operator_type"));
        User operator = userDAO.findById(operatorNationalCode);
        if (operator == null) {
            throw new MyEntityNotFoundException("operator id not found - " + operatorNationalCode);
        }
        theNormalContract.claim(operator, operatorType);
        return normalContractDAO.save(theNormalContract);
    }
}






