package ir.co.pna.exchange.config;


import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.*;
import ir.co.pna.exchange.service.account.AccountService;
import ir.co.pna.exchange.service.normalContract.NormalContractService;
import ir.co.pna.exchange.service.externalTransaction.ExternalTransactionService;
import ir.co.pna.exchange.service.judge.JudgeService;
import ir.co.pna.exchange.service.operationalOwner.OperationalOwnerService;
import ir.co.pna.exchange.service.publicOwner.PublicOwnerService;
import ir.co.pna.exchange.service.reporter.ReporterService;
import ir.co.pna.exchange.service.subcontract.SubcontractService;
import ir.co.pna.exchange.service.transaction.TransactionService;
import ir.co.pna.exchange.service.user.UserService;
import ir.co.pna.exchange.utility.XLSX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static ir.co.pna.exchange.utility.GlobalConstant.*;

@Configuration
class LoadDatabase {

    private ArrayList<PublicOwner> publicOwners;
    private ArrayList<User> users;
    private ArrayList<Judge> judges;
    private ArrayList<Reporter> reporters;
    private ArrayList<NormalContract> normalContracts;
    private ArrayList<Subcontract> subcontracts;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> internalTransactions;
    private ArrayList<ExternalTransaction> externalTransactions;

    private PublicOwnerService publicOwnerService;
    private OperationalOwnerService operationalOwnerService;
    private UserService userService;
    private JudgeService judgeService;
    private ReporterService reporterService;
    private AccountService accountService;
    private NormalContractService normalContractService;
    private SubcontractService subcontractService;
    private TransactionService transactionService;
    private ExternalTransactionService externalTransactionService;

    @Autowired
    public LoadDatabase(
            UserService userService,
            PublicOwnerService publicOwnerService,
            OperationalOwnerService operationalOwnerService,
            JudgeService judgeService,
            ReporterService reporterService,
            AccountService accountService,
            NormalContractService normalContractService,
            SubcontractService subcontractService,
            TransactionService transactionService,
            ExternalTransactionService externalTransactionService) {

        this.userService = userService;
        this.publicOwnerService = publicOwnerService;
        this.operationalOwnerService = operationalOwnerService;
        this.judgeService = judgeService;
        this.reporterService = reporterService;
        this.accountService = accountService;
        this.normalContractService = normalContractService;
        this.subcontractService = subcontractService;
        this.transactionService = transactionService;
        this.externalTransactionService = externalTransactionService;
    }

    private final String RELATIVE_PATH = System.getProperty("user.dir") + "/asset/";

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);


    @PostConstruct
    private void postConstruct() {

        users = new ArrayList<>();
        publicOwners = new ArrayList<>();
        judges = new ArrayList<>();
        reporters = new ArrayList<>();
        normalContracts = new ArrayList<>();
        subcontracts = new ArrayList<>();
        accounts = new ArrayList<>();
        internalTransactions = new ArrayList<>();
        externalTransactions = new ArrayList<>();

    }

    @Bean
    CommandLineRunner initDatabase() {

        System.out.println(RELATIVE_PATH + ",,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,");
        readExcelFiles();
        return args -> {
            loadUsers();
            loadPublicOwners();
            loadOperationalOwners();
            loadJudges();
            loadReporters();
//            loadAccounts();
//            loadContracts();
//            loadTransactions();
//            loadExternalTransactions();
        };
    }


    // load entities into database .....................................................................................

    private void loadPublicOwners() {
        for (PublicOwner publicOwner : publicOwners) {
            log.info("Preloading " + publicOwnerService.save(publicOwner));
        }

    }

    private void loadOperationalOwners() {
        operationalOwnerService.save(operationalExchangerOwner);
        operationalOwnerService.save(operationalExporterOwner);
        operationalOwnerService.save(operationalReturnOwner);
        operationalOwnerService.save(operationalClaimOwner);
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

    private void loadReporters() {
        for (Reporter reporter : reporters) {
            log.info("Preloading " + reporterService.save(reporter));
        }
    }

    private void loadAccounts() {
        for (Account account : accounts) {
            log.info("Preloading " + accountService.save(account));
        }
    }

    private void loadContracts() {
        for (NormalContract normalContract : normalContracts) {
//            log.info("Preloading " + normalContractService.save(normalContract));
        }

        for (Subcontract subContract : subcontracts) {
//            log.info("Preloading " + subcontractService.save(subContract));
        }
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

        initPublicOwnersFromFile();
        initUsersFromFile();
        initJudgesFromFile();
        initReportersFromFile();
//        initNormalContractsFromFile();
//        initSubContractsFromFile();
//        initChargeTransactionsFromFile();
//        initPaymentTransactionsFromFile();
//        initClaimTransactionsFromFile();
//        initJudgementTransactionsFromFile();
//        initReturnTransactions();

        System.out.println("files read from the database");
    }

    private void initPublicOwnersFromFile() {
        ArrayList<ArrayList<Object>> ownersData = null;
        ownersData = XLSX.readFile(new File(RELATIVE_PATH + "owners.xlsx").getAbsolutePath());
        //
        for (int i = 1; i < ownersData.size(); i++) {

            String bankAccountId = ownersData.get(i).get(0).toString();

            if (getOwnerIndex(bankAccountId) == -1) {
                OwnerType ownerType = OwnerType.valueOf(ownersData.get(i).get(1).toString().toUpperCase());
                PublicOwner publicOwner = null;
                switch (ownerType) {
                    case IMPORTER:
                        publicOwner = new Importer(bankAccountId);
                        break;
                    case EXCHANGER:
                        publicOwner = new Exchanger(bankAccountId);
                        break;
                    case EXPORTER:
                        publicOwner = new Exporter(bankAccountId);
                        break;
                }

                publicOwners.add(publicOwner);

            } else {
                System.out.println("Error: owner with bankAccountId " + bankAccountId + " already exists!");
            }
        }

        log.info("owners read from the file");

    }

    private void initUsersFromFile() {
        ArrayList<ArrayList<Object>> userData = XLSX.readFile(new File(RELATIVE_PATH + "users_owners.xlsx").getAbsolutePath());

        for (int i = 1; i < userData.size(); i++) {

            String nationalCode = userData.get(i).get(0).toString();
            String firstName = userData.get(i).get(1).toString();
            String lastName = userData.get(i).get(2).toString();
            String ownerBankAccountId = userData.get(i).get(3).toString();


            int index = getUserIndex(nationalCode);
            User user = null;

            boolean shouldBeAdded = false;

            if (index == -1) {

                user = new User(firstName, lastName, nationalCode);

                users.add(user);
//                saveObject(user);
                shouldBeAdded = true;


            } else {
                user = users.get(index);
                if (isUserRecordDuplicate(user, ownerBankAccountId)) {
                    System.out.println("Error: user with nationalCode " + nationalCode + " and ownerBankAccountId " + ownerBankAccountId + " already exists!");

                } else {
                    shouldBeAdded = true;
                }
            }

            if (shouldBeAdded) {


                for (PublicOwner publicOwner : publicOwners) {
                    if (publicOwner.getBankAccountId().equals(ownerBankAccountId)) {
                        publicOwner.addUser(user);
                        break;
                    }
                }
            }
        }

        log.info("users read from the file");
    }

    private void initJudgesFromFile() {

        ArrayList<ArrayList<Object>> judgesData = XLSX.readFile(new File(RELATIVE_PATH + "judges.xlsx").getAbsolutePath());
        for (int i = 1; i < judgesData.size(); i++) {

            String national_id = judgesData.get(i).get(0).toString();
            if (getJudgeIndex(national_id) == -1) {
                String name = judgesData.get(i).get(1).toString();
                Judge judge = new Judge(name, national_id);
                judges.add(judge);
            } else {
                System.out.println("Error: judge with nationalId " + national_id + " already exists!");
            }
        }


        log.info("judges read from the file");
    }

    private void initReportersFromFile() {

        ArrayList<ArrayList<Object>> reportersData = XLSX.readFile(new File(RELATIVE_PATH + "reporters.xlsx").getAbsolutePath());
        for (int i = 1; i < reportersData.size(); i++) {

            String username = reportersData.get(i).get(0).toString();
            if (getReporterIndex(username) == -1) {
                String fullName = reportersData.get(i).get(1).toString();
                Reporter reporter = new Reporter(fullName, username);
                reporters.add(reporter);
            } else {
                System.out.println("Error: reporter with username " + username + " already exists!");
            }
        }


        log.info("reporters read from the file");
    }

//    private void initNormalContractsFromFile() {
//
//        ArrayList<ArrayList<Object>> normalContractsData = XLSX.readFile(new File(RELATIVE_PATH + "normalContracts.xlsx").getAbsolutePath());
//
//        for (int i = 1; i < normalContractsData.size(); i++) {
//
//            int id = Integer.parseInt(normalContractsData.get(i).get(0).toString());
//
//            if (getNormalContractIndex(id) == -1) {
//
//                SettlementType settlementType = SettlementType.valueOf(normalContractsData.get(i).get(1).toString().toUpperCase());
//
//                String srOwnerBankAccountId = normalContractsData.get(i).get(2).toString();
//
//                Owner srcOwner = null;
//                int srcOwnerIndex = getOwnerIndex(srOwnerBankAccountId);
//
//                if (srcOwnerIndex == -1) {
//                    System.out.println("Error: no srcOwner match with bankAccountId " + srOwnerBankAccountId + " for contract with id " + id);
//                } else {
//                    srcOwner = owners.get(srcOwnerIndex);
//                }
//
//
//                String dstOwnerBankAccountId = normalContractsData.get(i).get(3).toString();
//
//                Owner dstOwner = null;
//                int dstOwnerIndex = getOwnerIndex(dstOwnerBankAccountId);
//
//                if (dstOwnerIndex == -1) {
//                    System.out.println("Error: no dstOwner match with bankAccountId " + dstOwnerBankAccountId + " for contract with id " + id);
//                } else {
//                    dstOwner = owners.get(dstOwnerIndex);
//                }
//
//
//                String judgeNationalId = normalContractsData.get(i).get(4).toString();
//                Judge judge = null;
//                int judgeIndex = getJudgeIndex(judgeNationalId);
//
//                if (judgeIndex == -1) {
//                    System.out.println("Error: no judge match with bankAccountId " + judgeNationalId + " for contract with id " + id);
//                } else {
//                    judge = judges.get(judgeIndex);
//                }
//
//                String strDate = normalContractsData.get(i).get(5).toString();
//                String[] times = strDate.split("/");
//                int year = Integer.parseInt(times[0]);
//                int month = Integer.parseInt(times[1]);
//                int date = Integer.parseInt(times[2]);
//
//                Calendar expireDate = Calendar.getInstance();
//                expireDate.set(year, month, date, 0, 0, 0);
//
//                long valueInRial = Long.parseUnsignedLong(normalContractsData.get(i).get(6).toString());
//                long remittanceValue = Long.parseUnsignedLong(normalContractsData.get(i).get(7).toString());
//                Currency remittanceCurrency = Currency.valueOf(normalContractsData.get(i).get(8).toString().toUpperCase());
//                String description = normalContractsData.get(i).get(9).toString();
////
//
//                NormalContract normalContract = new NormalContract(
////                        subcontractService,
//                        settlementType, srcOwner, dstOwner, judge, expireDate.getTimeInMillis(), valueInRial, remittanceValue, remittanceCurrency, description);
//
//                Account returnAccount = new Account(AccountType.RETURN, returnOwner, expireDate.getTimeInMillis(), normalContract);
//                Account claimAccount = new Account(AccountType.CLAIM, claimOwner, expireDate.getTimeInMillis(), normalContract);
//                Account exchangerAccount = new Account(AccountType.EXCHANGER, dstOwner, expireDate.getTimeInMillis(), normalContract);
//
//                normalContract.setReturnAccount(returnAccount);
//                normalContract.setExchangerAccount(exchangerAccount);
//
//                accounts.add(returnAccount);
//                accounts.add(claimAccount);
//                accounts.add(exchangerAccount);
//
//                normalContracts.add(normalContract);
////                saveObject(normalContract);
//
//
//            } else {
//                System.out.println("Error: normalContract with id " + id + " already exists!");
//            }
//        }
//
//
//    }

//    private void initSubContractsFromFile() {
//
//        ArrayList<ArrayList<Object>> subContractsData = XLSX.readFile(new File(RELATIVE_PATH + "subContracts.xlsx").getAbsolutePath());
//
//        for (int i = 1; i < subContractsData.size(); i++) {
//
//            int id = Integer.parseInt(subContractsData.get(i).get(0).toString());
//
//            if (getSubContractIndex(id) == -1) {
//
//                int parentId = Integer.parseInt(subContractsData.get(i).get(1).toString());
//
//                NormalContract parent = normalContracts.get(getNormalContractIndex(parentId));
//
//                Owner srcOwner = parent.getDstOwner();
//
//                String dstOwnerBankAccountId = subContractsData.get(i).get(2).toString();
//                Owner dstOwner = null;
//                int dstOwnerIndex = getOwnerIndex(dstOwnerBankAccountId);
//
//                if (dstOwnerIndex == -1) {
//                    System.out.println("Error: no dstOwner match with bankAccountId " + dstOwnerBankAccountId + " for contract with id " + id);
//                } else {
//                    dstOwner = owners.get(dstOwnerIndex);
//
//                    String strDate = subContractsData.get(i).get(3).toString();
//                    String[] times = strDate.split("/");
//                    int year = Integer.parseInt(times[0]);
//                    int month = Integer.parseInt(times[1]);
//                    int date = Integer.parseInt(times[2]);
//
//                    Calendar expireDate = Calendar.getInstance();
//                    expireDate.set(year, month, date, 0, 0, 0);
//
//                    long valueInRial = Long.parseUnsignedLong(subContractsData.get(i).get(4).toString());
//                    long remittanceValue = Long.parseUnsignedLong(subContractsData.get(i).get(5).toString());
//                    String description = subContractsData.get(i).get(6).toString();
//
//
////                saveObject(exporterAccount);
////                saveObject(returnAccount);
////                saveObject(claimAccount);
//
//                    Subcontract subContract = parent.createSubcontract(expireDate.getTimeInMillis(), dstOwner, valueInRial, remittanceValue, description);
//
//                    Account returnAccount = new Account(AccountType.RETURN, dstOwner, expireDate.getTimeInMillis(), subContract);
//                    Account claimAccount = new Account(AccountType.CLAIM, dstOwner, expireDate.getTimeInMillis(), subContract);
//                    Account exporterAccount = new Account(AccountType.EXPORTER, dstOwner, expireDate.getTimeInMillis(), subContract);
//
//                    subContract.setClaimAccount(claimAccount);
//                    subContract.setExporterAccount(exporterAccount);
//
//                    accounts.add(returnAccount);
//                    accounts.add(claimAccount);
//                    accounts.add(exporterAccount);
//
//
//                    subcontracts.add(subContract);
////                saveObject(subContract);
//                }
//
//            } else {
//
//                System.out.println("Error: subContract with id " + id + " already exists!");
//            }
//        }
//    }

//    private void initChargeTransactionsFromFile() {
//
//        ArrayList<ArrayList<Object>> paymentsData = XLSX.readFile(new File(RELATIVE_PATH + "chargeTransactions.xlsx").getAbsolutePath());
//
//        for (int i = 1; i < paymentsData.size(); i++) {
//
//            int bankTransactionId = Integer.parseInt(paymentsData.get(i).get(0).toString());
//            int contractId = Integer.parseInt(paymentsData.get(i).get(1).toString());
//            String srcOwnerBankAccountId = paymentsData.get(i).get(2).toString();
//            String dstOwnerBankAccountId = paymentsData.get(i).get(3).toString();
//            String userNationalCode = paymentsData.get(i).get(4).toString();
//
//            Owner srcOwner = owners.get(getOwnerIndex(srcOwnerBankAccountId));
//            Owner dstOwner = owners.get(getOwnerIndex(dstOwnerBankAccountId));
//            User operator = users.get(getUserIndex(userNationalCode));
//            long valueInRial = Long.parseLong(paymentsData.get(i).get(4).toString());
//
//            NormalContract normalContract = normalContracts.get(getNormalContractIndex(contractId));
//            normalContract.charge(operator, TransactionOperatorType.NORMAL_USER);
////            ExternalTransaction newExternalTransaction = new ExternalTransaction(bankTransactionId, newInternalTransaction, Calendar.getInstance().getTimeInMillis());
////            newInternalTransaction.setExternalTransaction(newExternalTransaction);
////
////            internalTransactions.add(newInternalTransaction);
////            externalTransactions.add(newExternalTransaction);
//
////            saveObject(newInternalTransaction);
////            saveObject(newExternalTransaction);
//
//        }
//    }

//    private void initPaymentTransactionsFromFile() {
//        ArrayList<ArrayList<Object>> paymentsData = XLSX.readFile(new File(RELATIVE_PATH + "paymentTransactions.xlsx").getAbsolutePath());
//
//        for (int i = 1; i < paymentsData.size(); i++) {
//
//            int bankTransactionId = Integer.parseInt(paymentsData.get(i).get(0).toString());
//            int subContractId = Integer.parseInt(paymentsData.get(i).get(1).toString());
//            String userNationalCode = paymentsData.get(i).get(2).toString();
//            long valueInRial = Long.parseLong(paymentsData.get(i).get(3).toString());
//            NormalContract normalContract = normalContracts.get(getNormalContractIndex(subcontracts.get(getSubContractIndex(subContractId)).getParent().getId()));
//            Subcontract subcontract = subcontracts.get(getSubContractIndex(subContractId));
//
//            User operator = users.get(getUserIndex(userNationalCode));
////            Transaction newInternalTransaction = normalContract.pay(operator, subContractId, TransactionOperatorType.USER);
////            ExternalTransaction newExternalTransaction = new ExternalTransaction(bankTransactionId, newInternalTransaction, Calendar.getInstance().getTimeInMillis());
////            internalTransactions.add(newInternalTransaction);
////            externalTransactions.add(newExternalTransaction);
//
////            saveObject(newInternalTransaction);
////            saveObject(newExternalTransaction);
//        }
//    }

//    private void initClaimTransactionsFromFile() {
//        ArrayList<ArrayList<Object>> claimsData = XLSX.readFile(new File(RELATIVE_PATH + "claimTransactions.xlsx").getAbsolutePath());
//
//        for (int i = 1; i < claimsData.size(); i++) {
//            int bankTransactionId = Integer.parseInt(claimsData.get(i).get(0).toString());
//            int contractId = Integer.parseInt(claimsData.get(i).get(1).toString());
//
//            String userNationalCode = claimsData.get(i).get(2).toString();
//            User operator = users.get(getUserIndex(userNationalCode));
//
//            NormalContract normalContract = normalContracts.get(getNormalContractIndex(contractId));
//
//            int[] childrenTransactionIds = new int[normalContract.getSubcontracts().size()];
//            for (int j = 0; j < childrenTransactionIds.length; j++) {
//                childrenTransactionIds[j] = generateUniqueTransactionId();
//            }
//
//            normalContract.claim(operator, TransactionOperatorType.NORMAL_USER);
//
//            ExternalTransaction[] newExternalTransactions = new ExternalTransaction[newInternalTransactions.length];
//
//            newExternalTransactions[0] = new ExternalTransaction(bankTransactionId, newInternalTransactions[0], Calendar.getInstance().getTimeInMillis());
//
//            for (int j = 1; j < newExternalTransactions.length; j++) {
//
//                newExternalTransactions[j] = new ExternalTransaction(bankTransactionId + j, newInternalTransactions[j], Calendar.getInstance().getTimeInMillis());
//            }
//
//            internalTransactions.addAll(Arrays.asList(newInternalTransactions));
//            externalTransactions.addAll(Arrays.asList(newExternalTransactions));
//
//            for (Transaction transaction : newInternalTransactions) {
////                saveObject(transaction);
//            }
//
//
//            for (ExternalTransaction transaction : newExternalTransactions) {
////                saveObject(transaction);
//            }
//
//
//        }
//    }
//
//    private void initJudgementTransactionsFromFile() {
//        ArrayList<ArrayList<Object>> votesData = XLSX.readFile(new File(RELATIVE_PATH + "judgementTransactions.xlsx").getAbsolutePath());
//
//        for (int i = 1; i < votesData.size(); i++) {
//            int bankTransactionId = Integer.parseInt(votesData.get(i).get(0).toString());
//            int contractId = Integer.parseInt(votesData.get(i).get(1).toString());
//            String judgeNationalId = votesData.get(i).get(2).toString();
//            Judge judge = judges.get(getJudgeIndex(judgeNationalId));
//            JudgeVote judgeVote = JudgeVote.valueOf(votesData.get(i).get(3).toString().toUpperCase());
////            judge.judge(contractId, judgeVote);
//
//            NormalContract normalContract = null;
//            int index = getNormalContractIndex(contractId);
//            if (index == -1) {
//                normalContract = normalContracts.get(getNormalContractIndex(subcontracts.get(getSubContractIndex(contractId)).getParent().getId()));
//            } else {
//                normalContract = normalContracts.get(getNormalContractIndex(contractId));
//            }
//
//            User admin = users.get(getUserIndex("99")); // 99 is admin national code
//
//            Transaction newInternalTransaction;
//
//            ExternalTransaction newExternalTransaction = null;
//
////            if (judgeVote == JudgeVote.DONE) {
////                newInternalTransaction = normalContract.returnFromClaim2Exporter(contractId, ADMIN, admin);
////                newExternalTransaction = new ExternalTransaction(bankTransactionId, newInternalTransaction, Calendar.getInstance().getTimeInMillis());
////            } else {
//
////                newInternalTransaction = normalContract.returnFromClaim2Return(contractId, ADMIN, admin);
////                newExternalTransaction = new ExternalTransaction(bankTransactionId, newInternalTransaction, Calendar.getInstance().getTimeInMillis());
////            }
//
////            internalTransactions.add(newInternalTransaction);
////            externalTransactions.add(newExternalTransaction);
////
//        }
//    }
//
//    private void initReturnTransactions() { // return all charged but not payed contracts
//        for (NormalContract normalContract : normalContracts) {
////            System.out.println(normalContract.getId() + "" + normalContract.getStatus());
//            if (normalContract.getStatus() == ContractStatus.DOING_BY_EXCHANGER) {
//                User admin = users.get(getUserIndex("99")); // 99 is admin national code
//
//                Transaction newInternalTransaction = normalContract.returnFromExchanger2Return(ADMIN, admin);
//                ExternalTransaction newExternalTransaction = new ExternalTransaction(generateUniqueTransactionId() / 1000, newInternalTransaction, Calendar.getInstance().getTimeInMillis());
//                internalTransactions.add(newInternalTransaction);
//                externalTransactions.add(newExternalTransaction);
//
////                saveObject(newInternalTransaction);
////                saveObject(newExternalTransaction);
//            }
//        }
//    } //


    // helper methods ..................................................................................................

    private int getOwnerIndex(String bankAccountId) {
        for (int i = 0; i < publicOwners.size(); i++) {
            if (publicOwners.get(i).getBankAccountId().equals(bankAccountId)) {
                return i;
            }
        }
        return -1;
    }

    private int getUserIndex(String nationalCode) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(nationalCode)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isUserRecordDuplicate(User user, String ownerBankAccountId) {
        for (PublicOwner publicOwner : user.getPublicOwners()) {
            if (publicOwner.getBankAccountId().equals(ownerBankAccountId)) {
                return true;
            }
        }
        return false;
    }

    private int getJudgeIndex(String nationalId) {
        for (int i = 0; i < judges.size(); i++) {
            if (judges.get(i).getId().equals(nationalId)) {
                return i;
            }
        }
        return -1;
    }


    private int getReporterIndex(String reporterId) {
        for (int i = 0; i < judges.size(); i++) {
            if (judges.get(i).getId().equals(reporterId)) {
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
        for (int i = 0; i < subcontracts.size(); i++) {
            if (subcontracts.get(i).getId() == id) {
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