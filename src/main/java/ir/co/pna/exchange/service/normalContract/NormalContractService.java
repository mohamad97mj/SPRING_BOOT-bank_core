package ir.co.pna.exchange.service.normalContract;

import ir.co.pna.exchange.client.yaghut.YaghutClient;
import ir.co.pna.exchange.entity.NormalContract;
import ir.co.pna.exchange.entity.Subcontract;

import java.util.List;
import java.util.Map;


public interface NormalContractService {

    List<NormalContract> findAll();

    NormalContract findById(int id);

    NormalContract save(Map<String, Object> payload);

    NormalContract act(NormalContract theNormalContract, String action, Map<String, Object> payload);
}
