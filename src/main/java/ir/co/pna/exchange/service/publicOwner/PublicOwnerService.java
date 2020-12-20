package ir.co.pna.exchange.service.publicOwner;

import ir.co.pna.exchange.entity.ExternalTransaction;
import ir.co.pna.exchange.entity.PublicOwner;

import java.util.List;


public interface PublicOwnerService {

    List<PublicOwner> findAll();

    PublicOwner findById(String ownerId);

    String save(PublicOwner publicOwner);

    List<ExternalTransaction> getInExternalTransactions(String ownerId);

    List<ExternalTransaction> getOutExternalTransactions(String ownerId);

}
