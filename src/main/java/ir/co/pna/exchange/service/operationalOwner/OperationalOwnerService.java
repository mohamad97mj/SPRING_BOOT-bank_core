package ir.co.pna.exchange.service.operationalOwner;

import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.ExternalTransaction;
import ir.co.pna.exchange.entity.OperationalOwner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public interface OperationalOwnerService {

    List<OperationalOwner> findAll();

    OperationalOwner findByType(OwnerType ownerType);

    String save(OperationalOwner operationalOwner);

    List<ExternalTransaction> getInExternalTransactions(OwnerType ownerType);

    List<ExternalTransaction> getOutExternalTransactions(OwnerType ownerType);

    List<ExternalTransaction> getInExternalTransactionsTimeInterval(OwnerType ownerType, long from, long to);

    List<ExternalTransaction> getOutExternalTransactionsTimeInterval(OwnerType ownerType, long from, long to);

    byte[] getSystemOutput(long from, long to) throws IOException;
}
