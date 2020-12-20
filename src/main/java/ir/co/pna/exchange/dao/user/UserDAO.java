package ir.co.pna.exchange.dao.user;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.*;

import java.util.List;


public interface UserDAO {


	List<User> findAll();

	User findById(String userId);

	String save(User user);

	List<PublicOwner> getPublicOwners(String userId);

	PublicOwner getPublicOwner(String userId, String ownerId);

	List<NormalContract> getPublicOwnerInNormalContracts(String userId, String ownerId);

	NormalContract getOwnerInNormalContract(String userId, String ownerId, int normalContractId);

	List<NormalContract> getPublicOwnerOutNormalContracts(String userId, String ownerId);

	NormalContract getOwnerOutNormalContract(String userId, String ownerId, int normalContractId);

	List<Subcontract> getPublicOwnerInSubcontracts(String userId, String ownerId);

	Subcontract getOwnerInSubcontract(String userId, String ownerId, int subcontractId);

	List<Subcontract> getPublicOwnerInNormalContractSubcontracts(String userId, String ownerId, int normalContractId);

	Subcontract getPublicOwnerInNormalContractSubcontract(String userId, String ownerId, int normalContractId, int subContractId);
}
