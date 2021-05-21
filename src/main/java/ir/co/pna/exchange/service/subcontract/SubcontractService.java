package ir.co.pna.exchange.service.subcontract;

import ir.co.pna.exchange.entity.Subcontract;

import java.util.List;
import java.util.Map;


public interface SubcontractService {

    List<Subcontract> findAll();

    Subcontract findById(int id);

    Subcontract save(Map<String, Object> payload);

    Subcontract update(Subcontract theSubcontract, Map<String, Object> payload);

    Subcontract act(Subcontract theSubcontract, String action, Map<String, Object> payload);

    Subcontract judge(Subcontract theSubcontract, Map<String, Object> payload);
}
