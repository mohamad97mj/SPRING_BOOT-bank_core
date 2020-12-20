package ir.co.pna.exchange.utility;


import ir.co.pna.exchange.entity.*;

import java.util.ArrayList;

public class Log {

    public static void printRegisteredOwners(ArrayList<PublicOwner> publicOwners) {
        System.out.println("owners are:");
        for (PublicOwner publicOwner : publicOwners) {
            System.out.println("  - bankAccountID: " + publicOwner.getBankAccountId()
                    + ", ownerType: " + publicOwner.getOwnerType());
            System.out.println("    * list of users: ");
            for (User user : publicOwner.getUsers()) {
                System.out.println("      + nationalCode: " + user.getId());
            }
        }
    }

    public static void printRegisteredUsers(ArrayList<User> users) {
        System.out.println("users are:");
        for (User user : users) {
            System.out.println("  - firstName: " + user.getFirstName()
                    + ", lastName: " + user.getLastName()
                    + ", nationalCode: " + user.getId());

            System.out.println("    * list of owners: ");
            for (PublicOwner publicOwner : user.getPublicOwners()) {
                System.out.println("      + bankAccountID: " + publicOwner.getBankAccountId());
            }
        }
    }

    public static void printRegisteredJudges(ArrayList<Judge> judges) {
        System.out.println("judges are:");
        for (Judge judge : judges) {
            System.out.println("  - name: " + judge.getName()
                    + ", nationalId: " + judge.getId());
            System.out.println("    * list of normalContracts:");
            for (NormalContract normalContract : judge.getNormalContracts()) {
                System.out.println("      + id: " + normalContract.getId());
            }
        }
    }

    public static void printNormalContracts(ArrayList<NormalContract> normalContracts) {
        System.out.println("normalContracts are:");
        for (NormalContract normalContract : normalContracts) {
            System.out.println("  - id: " + normalContract.getId()
                    + ", settlementType: " + normalContract.getSettlementType()
                    + ", srcOwner: " + normalContract.getSrcPublicOwner().getBankAccountId()
                    + ", dstOwner: " + normalContract.getDstPublicOwner().getBankAccountId()
                    + ", judge: " + normalContract.getJudge().getId()
                    + ", expireDate: " + normalContract.getExpireDate()
                    + ", valueInRial: " + normalContract.getValueInRial()
                    + ", remittanceValue: " + normalContract.getRemittanceValue()
                    + ", remittanceCurrency: " + normalContract.getRemittanceCurrency()
                    + ", description: " + normalContract.getDescription()
                    + ", exchangerAccount: " + normalContract.getExchangerAccount().getId()
                    + ", returnAccount: " + normalContract.getReturnAccount().getId()
            );
            System.out.println("    * list of subContracts: ");
            for (Subcontract subContract : normalContract.getSubcontracts()) {
                System.out.println("      + id: " + subContract.getId());
            }
        }

    }

    public static void printAccounts(ArrayList<Account> accounts) {
        System.out.println("accounts are:");
        for (Account account : accounts) {
            System.out.println("  - accountID: " + account.getId()
                    + ", accountType: " + account.getType()
                    + ", owner: " + account.getOwner().getBankAccountId()
                    + ", contract: " + account.getContract().getId()
                    + ", expireDate: " + account.getExpireTime()
                    + ", credit: " + account.getCredit());
        }
    }

    public static void printSubContracts(ArrayList<Subcontract> subcontracts) {

        System.out.println("subContracts are:");
        for (Subcontract subcontract : subcontracts) {
            System.out.println("  - id: " + subcontract.getId()
                    + ", parent: " + subcontract.getParent().getId()
                    + ", srcOwner: " + subcontract.getParent().getSrcPublicOwner().getBankAccountId()
                    + ", dstOwner: " + subcontract.getDstPublicOwner().getBankAccountId()
                    + ", expireDate: " + subcontract.getExpireDate()
                    + ", valueInRial: " + subcontract.getValueInRial()
                    + ", remittanceValue: " + subcontract.getRemittanceValue()
                    + ", description: " + subcontract.getDescription()
                    + ", exporterAccount: " + subcontract.getExporterAccount().getId()
                    + ", claimAccount: " + subcontract.getClaimAccount().getId()
            );
        }
    }

    public static void printJudgesVotes(ArrayList<NormalContract> normalContracts) {
        for (NormalContract normalContract : normalContracts) {
            System.out.println("judgesVotes are:");
            System.out.println("  - contractId: " + normalContract.getId() + ", judge: " + normalContract.getJudge().getId() + ", status: " + normalContract.getStatus());
        }
    }

    public static void printInternalTransactions(ArrayList<Transaction> internalTransactions) {
        System.out.println("internal transactions are:");
        for (Transaction transaction : internalTransactions) {
            System.out.println("  - id: " + transaction.getId()
                    + ", operator: " + transaction.getOperator().getId()
                    + ", operatorType: " + transaction.getOperatorType()
                    + ", transactionClassType: " + ((transaction instanceof InternalTransaction) ? "internal" : (transaction instanceof OneSideInternalTransaction) ? "externalIn" : "externalOut")
                    + ", transactionType: " + transaction.getTransactionType()
                    + ((transaction instanceof InternalTransaction) ? ", srcAccount: " + ((InternalTransaction) transaction).getSrcAccount().getId() : ", srcOwner: " + ((OneSideInternalTransaction) transaction).getSrc().getBankAccountId())
                    + ", dstAccount: "  + ((transaction instanceof InternalTransaction) ? + ((InternalTransaction) transaction).getDstAccount().getId() :  + ((OneSideInternalTransaction) transaction).getDstAccount().getId())
                    + ", amount: " + transaction.getAmount()

            );
        }
    }

    public static void printExternalTransactions(ArrayList<ExternalTransaction> externalTransactions) {
        System.out.println("external transactions are:");
        for (ExternalTransaction transaction : externalTransactions) {
            System.out.println("  - bankTransactionId: " + transaction.getBankTransactionId()
                    + ", src: " + transaction.getInternalTransaction().getSrcAccount().getOwner().getBankAccountId()
                    + ", dst: " + transaction.getInternalTransaction().getDstAccount().getOwner().getBankAccountId()
                    + ", internalTransaction: " + transaction.getInternalTransaction().getId()
                    + ", amount: " + transaction.getInternalTransaction().getAmount()

            );
        }
    }
}
