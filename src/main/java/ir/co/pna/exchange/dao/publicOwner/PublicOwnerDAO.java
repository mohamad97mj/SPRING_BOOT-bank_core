package ir.co.pna.exchange.dao.publicOwner;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.entity.PublicOwner;

import java.util.List;


public interface PublicOwnerDAO {


    List<PublicOwner> findAll();

    PublicOwner findById(String id);

    String save(PublicOwner publicOwner);

}
