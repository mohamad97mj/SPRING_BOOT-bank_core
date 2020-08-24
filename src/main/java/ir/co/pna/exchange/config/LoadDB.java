package ir.co.pna.exchange.config;


import ir.co.pna.exchange.emum.*;
import ir.co.pna.exchange.entity.*;
import ir.co.pna.exchange.service.Account.AccountService;
import ir.co.pna.exchange.service.Contract.ContractService;
import ir.co.pna.exchange.service.ExternalTransaction.ExternalTransactionService;
import ir.co.pna.exchange.service.Judge.JudgeService;
import ir.co.pna.exchange.service.Owner.OwnerService;
import ir.co.pna.exchange.service.Transaction.TransactionService;
import ir.co.pna.exchange.service.User.UserService;
import ir.co.pna.exchange.utility.XLSX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.ThreadLocalRandom;

import static ir.co.pna.exchange.emum.TransactionOperatorType.ADMIN;

@Configuration
class LoadDatabase {

    private User admin;
    private Owner exchangerOwner, returnOwner, claimOwner, exporterOwner;
    private ArrayList<Owner> owners;
    private ArrayList<User> users;
    private ArrayList<Judge> judges;
    private ArrayList<NormalContract> normalContracts;
    private ArrayList<SubContract> subContracts;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> internalTransactions;
    private ArrayList<ExternalTransaction> externalTransactions;

    private OwnerService ownerService;
    private UserService userService;
    private JudgeService judgeService;
    private AccountService accountService;
    private ContractService contractService;
    private TransactionService transactionService;
    private ExternalTransactionService externalTransactionService;

    @Autowired
    public LoadDatabase(
            OwnerService ownerService,
            UserService userService,
            JudgeService judgeService,
            AccountService accountService,
            ContractService contractService,
            TransactionService transactionService,
            ExternalTransactionService externalTransactionService) {

        this.ownerService = ownerService;
        this.userService = userService;
        this.judgeService = judgeService;
        this.accountService = accountService;
        this.contractService = contractService;
        this.transactionService = transactionService;
        this.externalTransactionService = externalTransactionService;
    }

    private final String RELATIVE_PATH = System.getProperty("user.dir") + "/src/main/java/ir/co/pna/exchange/asset/";

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);


    @PostConstruct
    private void postConstruct() {

        admin = new User("admin", "admin", "99");
        exchangerOwner = new Owner(9191, OwnerType.EXCHANGER);
        returnOwner = new Owner(9292, OwnerType.RETURN);
        claimOwner = new Owner(9393, OwnerType.CLAIM);
        exporterOwner = new Owner(9494, OwnerType.EXPORTER);

        owners = new ArrayList<>();
        users = new ArrayList<>();
        judges = new ArrayList<>();
        normalContracts = new ArrayList<>();
        subContracts = new ArrayList<>();
        accounts = new ArrayList<>();
        internalTransactions = new ArrayList<>();
        externalTransactions = new ArrayList<>();

    }

    @Bean
    CommandLineRunner initDatabase() {

        readExcelFiles();
        return args -> {
            loadOwners();
            loadUsers();
            loadJudges();
            loadAccounts();
            loadContracts();
            loadTransactions();
            loadExternalTransactions();
        };
    }


    // load entities into database .....................................................................................

    private void loadOwners() {
        for (Owner owner : owners) {
            log.info("Preloading " + ownerService.save(owner));
        }
    }

    private void loadUsers() {
        for (User user : users) {
            log.info("Preloading " + userService.save(user));
        }
    }

    private void loadJudges() {
        for (Judge judge : judges) {
            log.info("Preloading " + judgeService.save(judge));
        }
    }

    private void loadAccounts() {
        for (Account account : accounts) {
            log.info("Preloading " + accountService.save(account));
        }
    }

    private void loadContracts() {
        for (NormalContract normalContract : normalContracts) {
            log.info("Preloading " + contractService.save(normalContract));
        }

//        for (SubContract subContract : subContracts) {
//            log.info("Preloading " + contractService.save(subContract));
//        }
    }

    private void loadTransactions() {
        for (Transaction transaction : internalTransactions) {
            log.info("Preloading " + transactionService.save(transaction));
        }
    }

    private void loadExternalTransactions() {
        for (ExternalTransaction externalTransaction : externalTransactions) {
            log.info("Preloading " + externalTransactionService.save(externalTransaction));
        }
    }

    // read form files methods .........................................................................................

    void readExcelFiles() {

        initOwnersFromFile();
        initUsersFromFile();
        initJudgesFromFile();
        initNormalContractsFromFile();
        initSubContractsFromFile();
        initChargeTransactionsFromFile();
        initPaymentTransactionsFromFile();
        initClaimTransactionsFromFile();
        initJudgementTransactionsFromFile();
        initReturnTransactions();

        System.out.println("files read from the database");
    }

    private void initOwnersFromFile() {
        ArrayList<ArrayList<Object>> ownersData = XLSX.readFile(RELATIVE_PATH + "owners.xlsx");
//
        for (int i = 1; i < ownersData.size(); i++) {

            String strBankAccountId = ownersData.get(i).get(0).toString();
            long bankAccountId = Long.parseUnsignedLong(strBankAccountId);

            if (getOwnerIndex(bankAccountId) == -1) {
                OwnerType ownerType = OwnerType.valueOf(ownersData.get(i).get(1).toString().toUpperCase());
                Owner owner = new Owner(bankAccountId, ownerType);
                owners.add(owner);
//                saveObject(owner);

            } else {
                System.out.println("Error: owner with bankAccountId " + strBankAccountId + " already exists!");
            }
        }

        log.info("owners read from the file");

    }

    private void initUsersFromFile() {
        ArrayList<ArrayList<Object>> userData = XLSX.readFile(RELATIVE_PATH + "users.xlsx");

        for (int i = 1; i < userData.size(); i++) {

            String strNationalCode = userData.get(i).get(0).toString();
            String firstName = userData.get(i).get(1).toString();
            String lastName = userData.get(i).get(2).toString();
            String strOwnerBankAccountID = userData.get(i).get(3).toString();

            long ownerBankAccountId = Long.parseUnsignedLong(strOwnerBankAccountID);

            long nationalCode = Long.parseUnsignedLong(strNationalCode);

            int index = getUserIndex(nationalCode);
            User user = null;

            boolean shouldBeAdded = false;

            if (index == -1) {

                user = new User(firstName, lastName, strNationalCode);

                users.add(user);
//                saveObject(user);
                shouldBeAdded = true;


            } else {
                user = users.get(index);
                if (isUserRecordDuplicate(user, ownerBankAccountId)) {
                    System.out.println("Error: user with nationalCode " + strNationalCode + " and ownerBankAccountId " + strOwnerBankAccountID + " already exists!");

                } else {
                    shouldBeAdded = true;
                }
            }

            if (shouldBeAdded) {


                for (Owner owner : owners) {
                    if (owner.getBankAccountId() == ownerBankAccountId) {
                        owner.addUser(user);
                        break;
                    }
                }
            }
        }

        log.info("users read from the file");
    }

    private void initJudgesFromFile() {

        ArrayList<ArrayList<Object>> judgesData = XLSX.readFile(RELATIVE_PATH + "judges.xlsx");
        for (int i = 1; i < judgesData.size(); i++) {

            String strNationalId = judgesData.get(i).get(0).toString();
            long national_id = Long.parseUnsignedLong(strNationalId);
            if (getJudgeIndex(national_id) == -1) {
                String name = judgesData.get(i).get(1).toString();
                Judge judge = new Judge(name, national_id);
                judges.add(judge);
            } else {
                System.out.println("Error: judge with nationalId " + strNationalId + " already exists!");
            }
        }


        log.info("judges read from the file");
    }

    private void initNormalContractsFromFile() {

        ArrayList<ArrayList<Object>> normalContractsData = XLSX.readFile(RELATIVE_PATH + "normalContracts.xlsx");

        for (int i = 1; i < normalContractsData.size(); i++) {

            int id = Integer.parseInt(normalContractsData.get(i).get(0).toString());

            if (getNormalContractIndex(id) == -1) {

                SettlementType settlementType = SettlementType.valueOf(normalContractsData.get(i).get(1).toString().toUpperCase());

                String strSrcOwnerBankAccountId = normalContractsData.get(i).get(2).toString();
                long ownerBankAccountId = Long.parseUnsignedLong(strSrcOwnerBankAccountId);

                Owner srcOwner = null;
                int srcOwnerIndex = getOwnerIndex(ownerBankAccountId);

                if (srcOwnerIndex == -1) {
                    System.out.println("Error: no srcOwner match with bankAccountId " + strSrcOwnerBankAccountId + " for contract with id " + id);
                } else {
                    srcOwner = owners.get(srcOwnerIndex);
                }


                String strDstOwnerBankAccountId = normalContractsData.get(i).get(3).toString();
                long dstOwnerBankAccountId = Long.parseUnsignedLong(strDstOwnerBankAccountId);

                Owner dstOwner = null;
                int dstOwnerIndex = getOwnerIndex(dstOwnerBankAccountId);

                if (dstOwnerIndex == -1) {
                    System.out.println("Error: no dstOwner match with bankAccountId " + strDstOwnerBankAccountId + " for contract with id " + id);
                } else {
                    dstOwner = owners.get(dstOwnerIndex);
                }


                String strJudgeNationalId = normalContractsData.get(i).get(4).toString();
                long judgeNationalId = Long.parseUnsignedLong(strJudgeNationalId);
                Judge judge = null;
                int judgeIndex = getJudgeIndex(judgeNationalId);

                if (judgeIndex == -1) {
                    System.out.println("Error: no judge match with bankAccountId " + strJudgeNationalId + " for contract with id " + id);
                } else {
                    judge = judges.get(judgeIndex);
                }

                String strDate = normalContractsData.get(i).get(5).toString();
                String[] times = strDate.split("/");
                int year = Integer.parseInt(times[0]);
                int month = Integer.parseInt(times[1]);
                int date = Integer.parseInt(times[2]);

                Calendar expireDate = Calendar.getInstance();
                expireDate.set(year, month, date, 0, 0, 0);

                long valueInRial = Long.parseUnsignedLong(normalContractsData.get(i).get(6).toString());
                long remittanceValue = Long.parseUnsignedLong(normalContractsData.get(i).get(7).toString());
                Currency remittanceCurrency = Currency.valueOf(normalContractsData.get(i).get(8).toString().toUpperCase());
                String description = normalContractsData.get(i).get(9).toString();


//                saveObject(exchangerAccount);
//                saveObject(returnAccount);
//                saveObject(claimAccount);


                NormalContract normalContract = new NormalContract(contractService, id, settlementType, srcOwner, dstOwner, judge, expireDate, valueInRial, remittanceValue, remittanceCurrency, description);

                Account returnAccount = new Account(generateUniqueAccountNumber(), AccountType.RETURN, dstOwner, expireDate, normalContract);
                Account claimAccount = new Account(generateUniqueAccountNumber(), AccountType.CLAIM, dstOwner, expireDate, normalContract);
                Account exchangerAccount = new Account(generateUniqueAccountNumber(), AccountType.EXCHANGER, dstOwner, expireDate, normalContract);

                normalContract.addAccount(returnAccount);
                normalContract.addAccount(claimAccount);
                normalContract.addAccount(exchangerAccount);


                accounts.add(returnAccount);
                accounts.add(claimAccount);
                accounts.add(exchangerAccount);


                normalContracts.add(normalContract);
//                saveObject(normalContract);


            } else {
                System.out.println("Error: normalContract with id " + id + " already exists!");
            }
        }


    }

    private void initSubContractsFromFile() {

        ArrayList<ArrayList<Object>> subContractsData = XLSX.readFile(RELATIVE_PATH + "subContracts.xlsx");

        for (int i = 1; i < subContractsData.size(); i++) {

            int id = Integer.parseInt(subContractsData.get(i).get(0).toString());

            if (getSubContractIndex(id) == -1) {

                int parentId = Integer.parseInt(subContractsData.get(i).get(1).toString());

                NormalContract parent = normalContracts.get(getNormalContractIndex(parentId));

                Owner srcOwner = parent.getDstOwner();

                String strDstOwnerBankAccountId = subContractsData.get(i).get(2).toString();
                long dstOwnerBankAccountId = Long.parseUnsignedLong(strDstOwnerBankAccountId);
                Owner dstOwner = null;
                int dstOwnerIndex = getOwnerIndex(dstOwnerBankAccountId);

                if (dstOwnerIndex == -1) {
                    System.out.println("Error: no dstOwner match with bankAccountId " + strDstOwnerBankAccountId + " for contract with id " + id);
                } else {
                    dstOwner = owners.get(dstOwnerIndex);

                    String strDate = subContractsData.get(i).get(3).toString();
                    String[] times = strDate.split("/");
                    int year = Integer.parseInt(times[0]);
                    int month = Integer.parseInt(times[1]);
                    int date = Integer.parseInt(times[2]);

                    Calendar expireDate = Calendar.getInstance();
                    expireDate.set(year, month, date, 0, 0, 0);

                    long valueInRial = Long.parseUnsignedLong(subContractsData.get(i).get(4).toString());
                    long remittanceValue = Long.parseUnsignedLong(subContractsData.get(i).get(5).toString());
                    String description = subContractsData.get(i).get(6).toString();


//                saveObject(exporterAccount);
//                saveObject(returnAccount);
//                saveObject(claimAccount);


                    SubContract subContract = parent.createSubContract(id, expireDate, srcOwner, dstOwner, valueInRial, remittanceValue, description);

                    Account returnAccount = new Account(generateUniqueAccountNumber(), AccountType.RETURN, dstOwner, expireDate, subContract);
                    Account claimAccount = new Account(generateUniqueAccountNumber(), AccountType.CLAIM, dstOwner, expireDate, subContract);
                    Account exporterAccount = new Account(generateUniqueAccountNumber(), AccountType.EXPORTER, dstOwner, expireDate, subContract);

                    subContract.addAccount(returnAccount);
                    subContract.addAccount(claimAccount);
                    subContract.addAccount(exporterAccount);

                    accounts.add(returnAccount);
                    accounts.add(claimAccount);
                    accounts.add(exporterAccount);


                    subContracts.add(subContract);
//                saveObject(subContract);
                }

            } else {

                System.out.println("Error: subContract with id " + id + " already exists!");
            }
        }
    }

    private void initChargeTransactionsFromFile() {

        ArrayList<ArrayList<Object>> paymentsData = XLSX.readFile(RELATIVE_PATH + "chargeTransactions.xlsx");

        for (int i = 1; i < paymentsData.size(); i++) {

            int bankTransactionId = Integer.parseInt(paymentsData.get(i).get(0).toString());
            int contractId = Integer.parseInt(paymentsData.get(i).get(1).toString());
            String strOwnerBankAccountId = paymentsData.get(i).get(2).toString();
            String strUserNationalCode = paymentsData.get(i).get(3).toString();

            long ownerBankAccountId = Long.parseUnsignedLong(strOwnerBankAccountId);
            long userNationalCode = Long.parseUnsignedLong(strUserNationalCode);

            Owner owner = owners.get(getOwnerIndex(ownerBankAccountId));
            User operator = users.get(getUserIndex(userNationalCode));
            long valueInRial = Long.parseLong(paymentsData.get(i).get(4).toString());

            NormalContract normalContract = normalContracts.get(getNormalContractIndex(contractId));
            Transaction newInternalTransaction = normalContract.charge(generateUniqueTransactionId(), operator, owner, valueInRial, TransactionOperatorType.NORMAL);
            ExternalTransaction newExternalTransaction = new ExternalTransaction(bankTransactionId, owner, exchangerOwner, newInternalTransaction);
            newInternalTransaction.setExternalTransaction(newExternalTransaction);

            internalTransactions.add(newInternalTransaction);
            externalTransactions.add(newExternalTransaction);

//            saveObject(newInternalTransaction);
//            saveObject(newExternalTransaction);

        }
    }

    private void initPaymentTransactionsFromFile() {
        ArrayList<ArrayList<Object>> paymentsData = XLSX.readFile(RELATIVE_PATH + "paymentTransactions.xlsx");

        for (int i = 1; i < paymentsData.size(); i++) {

            int bankTransactionId = Integer.parseInt(paymentsData.get(i).get(0).toString());
            int subContractId = Integer.parseInt(paymentsData.get(i).get(1).toString());
            String strUserNationalCode = paymentsData.get(i).get(2).toString();
            long valueInRial = Long.parseLong(paymentsData.get(i).get(3).toString());
            NormalContract normalContract = normalContracts.get(getNormalContractIndex(subContracts.get(getSubContractIndex(subContractId)).getParent().getId()));
            long userNationalCode = Long.parseUnsignedLong(strUserNationalCode);

            User operator = users.get(getUserIndex(userNationalCode));

            Transaction newInternalTransaction = normalContract.pay(generateUniqueTransactionId(), operator, subContractId, valueInRial, TransactionOperatorType.NORMAL);
            ExternalTransaction newExternalTransaction = new ExternalTransaction(bankTransactionId, exchangerOwner, exporterOwner, newInternalTransaction);
            internalTransactions.add(newInternalTransaction);
            externalTransactions.add(newExternalTransaction);

//            saveObject(newInternalTransaction);
//            saveObject(newExternalTransaction);
        }
    }

    private void initClaimTransactionsFromFile() {
        ArrayList<ArrayList<Object>> claimsData = XLSX.readFile(RELATIVE_PATH + "claimTransactions.xlsx");

        for (int i = 1; i < claimsData.size(); i++) {
            int bankTransactionId = Integer.parseInt(claimsData.get(i).get(0).toString());
            int contractId = Integer.parseInt(claimsData.get(i).get(1).toString());

            String strUserNationalCode = claimsData.get(i).get(2).toString();
            long userNationalCode = Long.parseUnsignedLong(strUserNationalCode);
            User operator = users.get(getUserIndex(userNationalCode));

            NormalContract normalContract = normalContracts.get(getNormalContractIndex(contractId));

            int[] childrenTransactionIds = new int[normalContract.getSubContracts().size()];
            for (int j = 0; j < childrenTransactionIds.length; j++) {
                childrenTransactionIds[j] = generateUniqueTransactionId();
            }

            Transaction[] newInternalTransactions = normalContract.claim(generateUniqueTransactionId(), childrenTransactionIds, operator, TransactionOperatorType.NORMAL);

            ExternalTransaction[] newExternalTransactions = new ExternalTransaction[newInternalTransactions.length];

            newExternalTransactions[0] = new ExternalTransaction(bankTransactionId, exchangerOwner, claimOwner, newInternalTransactions[0]);

            for (int j = 1; j < newExternalTransactions.length; j++) {

                newExternalTransactions[j] = new ExternalTransaction(bankTransactionId + j, exporterOwner, claimOwner, newInternalTransactions[j]);
            }

            internalTransactions.addAll(Arrays.asList(newInternalTransactions));
            externalTransactions.addAll(Arrays.asList(newExternalTransactions));

            for (Transaction transaction : newInternalTransactions) {
//                saveObject(transaction);
            }


            for (ExternalTransaction transaction : newExternalTransactions) {
//                saveObject(transaction);
            }


        }
    }

    private void initJudgementTransactionsFromFile() {
        ArrayList<ArrayList<Object>> votesData = XLSX.readFile(RELATIVE_PATH + "judgementTransactions.xlsx");

        for (int i = 1; i < votesData.size(); i++) {
            int bankTransactionId = Integer.parseInt(votesData.get(i).get(0).toString());
            int contractId = Integer.parseInt(votesData.get(i).get(1).toString());
            String judgeStrNationalId = votesData.get(i).get(2).toString();
            long judgeNationalId = Long.parseUnsignedLong(judgeStrNationalId);
            Judge judge = judges.get(getJudgeIndex(judgeNationalId));
            ContractStatus contractStatus = ContractStatus.valueOf(votesData.get(i).get(3).toString().toUpperCase());
            judge.judge(contractId, contractStatus);

            NormalContract normalContract = null;
            int index = getNormalContractIndex(contractId);
            if (index == -1) {
                normalContract = normalContracts.get(getNormalContractIndex(subContracts.get(getSubContractIndex(contractId)).getParent().getId()));
            } else {
                normalContract = normalContracts.get(getNormalContractIndex(contractId));
            }

            User admin = users.get(getUserIndex(99)); // 99 is admin national code

            Transaction newInternalTransaction;

            ExternalTransaction newExternalTransaction = null;

            if (contractStatus == ContractStatus.SUCCESSFUL) {
                newInternalTransaction = normalContract.returnFromClaim2Exporter(generateUniqueTransactionId(), contractId, ADMIN, admin);
                newExternalTransaction = new ExternalTransaction(bankTransactionId, claimOwner, exporterOwner, newInternalTransaction);
            } else {

                newInternalTransaction = normalContract.returnFromClaim2Return(generateUniqueTransactionId(), contractId, ADMIN, admin);
                newExternalTransaction = new ExternalTransaction(bankTransactionId, claimOwner, returnOwner, newInternalTransaction);
            }

            internalTransactions.add(newInternalTransaction);
            externalTransactions.add(newExternalTransaction);

//            saveObject(newInternalTransaction);
//            saveObject(newExternalTransaction);


        }
    }

    private void initReturnTransactions() { // return all charged but not payed contracts
        for (NormalContract normalContract : normalContracts) {
//            System.out.println(normalContract.getId() + "" + normalContract.getStatus());
            if (normalContract.getContractStatus() == ContractStatus.CHARGED) {
                User admin = users.get(getUserIndex(99)); // 99 is admin national code

                Transaction newInternalTransaction = normalContract.returnFromExchanger2Return(generateUniqueTransactionId(), ADMIN, admin);
                ExternalTransaction newExternalTransaction = new ExternalTransaction(generateUniqueTransactionId() / 1000, exchangerOwner, returnOwner, newInternalTransaction);
                internalTransactions.add(newInternalTransaction);
                externalTransactions.add(newExternalTransaction);

//                saveObject(newInternalTransaction);
//                saveObject(newExternalTransaction);
            }
        }
    } //


    // helper methods ..................................................................................................

    private int getOwnerIndex(long bankAccountId) {
        for (int i = 0; i < owners.size(); i++) {
            if (owners.get(i).getBankAccountId() == bankAccountId) {
                return i;
            }
        }
        return -1;
    }

    private int getUserIndex(long nationalCode) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getNationalCode() == nationalCode) {
                return i;
            }
        }
        return -1;
    }

    private boolean isUserRecordDuplicate(User user, long ownerBankAccountId) {
        for (Owner owner : user.getOwners()) {
            if (owner.getBankAccountId() == ownerBankAccountId) {
                return true;
            }
        }
        return false;
    }

    private int getJudgeIndex(long nationalId) {
        for (int i = 0; i < judges.size(); i++) {
            if (judges.get(i).getNationalId() == nationalId) {
                return i;
            }
        }
        return -1;
    }

    private int getNormalContractIndex(int id) {
        for (int i = 0; i < normalContracts.size(); i++) {
            if (normalContracts.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    private int getSubContractIndex(int id) {
        for (int i = 0; i < subContracts.size(); i++) {
            if (subContracts.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public long generateUniqueAccountNumber() {
        long min = 10000000;
        long max = 99999999;
        long randomNumber = -1;

        boolean isRandom = false;
        while (!isRandom) {
            isRandom = true;
            randomNumber = ThreadLocalRandom.current().nextLong(min, max + 1);

            for (Account account : accounts) {
                if (account.getId() == randomNumber) {
                    isRandom = false;
                    break;
                }
            }

        }

        return randomNumber;
    }

    public int generateUniqueTransactionId() {
        int min = 100000;
        int max = 999999;
        int randomNumber = -1;

        boolean isRandom = false;
        while (!isRandom) {
            isRandom = true;
            randomNumber = ThreadLocalRandom.current().nextInt(min, max + 1);

            for (Transaction transaction : internalTransactions) {
                if (transaction.getId() == randomNumber) {
                    isRandom = false;
                    break;
                }
            }

        }

        return randomNumber;
    }


}