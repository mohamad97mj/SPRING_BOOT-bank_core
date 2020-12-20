package ir.co.pna.exchange.service.user;

import ir.co.pna.exchange.entity.*;

import java.util.List;


public interface UserService {

	List<User> findAll();

	User findById(String userId);

	String save(User user);

	List<PublicOwner> getPublicOwners(String userId);

	PublicOwner getPublicOwner(String userId, String ownerId);

	List<NormalContract> getPublicOwnerInNormalContracts(String userId, String ownerId);

	NormalContract getPublicOwnerInNormalContract(String userId, String ownerId, int normalContractId);

	List<NormalContract> getPublicOwnerOutNormalContracts(String userId, String ownerId);

	NormalContract getPublicOwnerOutNormalContract(String userId, String ownerId, int normalContractId);

	List<Subcontract> getPublicOwnerInSubcontracts(String userId, String ownerId);

	Subcontract getPublicOwnerInSubcontract(String userId, String ownerId, int subcontractId);

	List<Subcontract> getPublicOwnerInNormalContractSubcontracts(String userId, String ownerId, int normalContractId);

	Subcontract getPublicOwnerInNormalContractSubcontract(String userId, String ownerId, int normalContractId , int subcontractId);
}
