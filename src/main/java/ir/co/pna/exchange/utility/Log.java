package ir.co.pna.exchange.utility;


import ir.co.pna.exchange.entity.*;

import java.util.ArrayList;

public class Log {

    public static void printRegisteredOwners(ArrayList<Owner> owners) {
        System.out.println("owners are:");
        for (Owner owner : owners) {
            System.out.println("  - bankAccountID: " + owner.getBankAccountId()
                    + ", ownerType: " + owner.getOwnerType());
            System.out.println("    * list of users: ");
            for (User user : owner.getUsers()) {
                System.out.println("      + nationalCode: " + user.getNationalCode());
            }
        }
    }

    public static void printRegisteredUsers(ArrayList<User> users) {
        System.out.println("users are:");
        for (User user : users) {
            System.out.println("  - firstName: " + user.getFirstName()
                    + ", lastName: " + user.getLastName()
                    + ", nationalCode: " + user.getNationalCode());

            System.out.println("    * list of owners: ");
            for (Owner owner : user.getOwners()) {
                System.out.println("      + bankAccountID: " + owner.getBankAccountId());
            }
        }
    }

    public static void printRegisteredJudges(ArrayList<Judge> judges) {
        System.out.println("judges are:");
        for (Judge judge : judges) {
            System.out.println("  - name: " + judge.getName()
                    + ", nationalId: " + judge.getNationalId());
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
                    + ", srcOwner: " + normalContract.getSrcOwner().getBankAccountId()
                    + ", dstOwner: " + normalContract.getDstOwner().getBankAccountId()
                    + ", judge: " + normalContract.getJudge().getNationalId()
                    + ", expireDate: " + normalContract.getExpireDate().getTime()
                    + ", valueInRial: " + normalContract.getValueInRial()
                    + ", remittanceValue: " + normalContract.getRemittanceValue()
                    + ", remittanceCurrency: " + normalContract.getRemittanceCurrency()
                    + ", description: " + normalContract.getDescription()
                    + ", exchangerAccount: " + normalContract.getExchangerAccount().getId()
                    + ", returnAccount: " + normalContract.getReturnAccount().getId()
                    + ", claimAccount: " + normalContract.getClaimAccount().getId()
            );
            System.out.println("    * list of subContracts: ");
            for (SubContract subContract : normalContract.getSubContracts()) {
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
                    + ", expireDate: " + account.getExpireTime().getTime()
                    + ", credit: " + account.getCredit());
        }
    }

    public static void printSubContracts(ArrayList<SubContract> subContracts) {

        System.out.println("subContracts are:");
        for (SubContract subcontract : subContracts) {
            System.out.println("  - id: " + subcontract.getId()
                    + ", parent: " + subcontract.getParent().getId()
                    + ", srcOwner: " + subcontract.getSrcOwner().getBankAccountId()
                    + ", dstOwner: " + subcontract.getDstOwner().getBankAccountId()
                    + ", expireDate: " + subcontract.getExpireDate().getTime()
                    + ", valueInRial: " + subcontract.getValueInRial()
                    + ", remittanceValue: " + subcontract.getRemittanceValue()
                    + ", description: " + subcontract.getDescription()
                    + ", exporterAccount: " + subcontract.getExporterAccount().getId()
                    + ", returnAccount: " + subcontract.getReturnAccount().getId()
                    + ", claimAccount: " + subcontract.getClaimAccount().getId()
            );
        }
    }

    public static void printJudgesVotes(ArrayList<NormalContract> normalContracts) {
        for (NormalContract normalContract : normalContracts) {
            System.out.println("judgesVotes are:");
            System.out.println("  - contractId: " + normalContract.getId() + ", judge: " + normalContract.getJudge().getNationalId() + ", status: " + normalContract.getContractStatus());
        }
    }

    public static void printInternalTransactions(ArrayList<Transaction> internalTransactions) {
        System.out.println("internal transactions are:");
        for (Transaction transaction : internalTransactions) {
            System.out.println("  - id: " + transaction.getId()
                    + ", operator: " + transaction.getOperator().getNationalCode()
                    + ", operatorType: " + transaction.getOperatorType()
                    + ", transactionClassType: " + ((transaction instanceof InternalTransaction) ? "internal" : (transaction instanceof OneSideInternalTransaction) ? "externalIn" : "externalOut")
                    + ", transactionType: " + transaction.getType()
                    + ((transaction instanceof InternalTransaction) ? ", srcAccount: " + ((InternalTransaction) transaction).getSrc().getId() : ", srcOwner: " + ((OneSideInternalTransaction) transaction).getSrc().getBankAccountId())
                    + ", dstAccount: "  + ((transaction instanceof InternalTransaction) ? + ((InternalTransaction) transaction).getDst().getId() :  + ((OneSideInternalTransaction) transaction).getDst().getId())
                    + ", amount: " + transaction.getAmount()

            );
        }
    }

    public static void printExternalTransactions(ArrayList<ExternalTransaction> externalTransactions) {
        System.out.println("external transactions are:");
        for (ExternalTransaction transaction : externalTransactions) {
            System.out.println("  - bankTransactionId: " + transaction.getBankTransactionId()
                    + ", src: " + transaction.getSrc().getBankAccountId()
                    + ", dst: " + transaction.getDst().getBankAccountId()
                    + ", internalTransaction: " + transaction.getInternalTransaction().getId()
                    + ", amount: " + transaction.getInternalTransaction().getAmount()

            );
        }
    }
}
