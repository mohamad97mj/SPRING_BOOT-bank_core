package ir.co.pna.exchange.dao.oprationalOwner;//package com.exchange.main.dao.Employee;

import ir.co.pna.exchange.emum.OwnerType;
import ir.co.pna.exchange.entity.OperationalOwner;

import java.util.List;


public interface OperationalOwnerDAO {


    List<OperationalOwner> findAll();

    OperationalOwner findByType(OwnerType type);

    String save(OperationalOwner operationalOwner);

}
