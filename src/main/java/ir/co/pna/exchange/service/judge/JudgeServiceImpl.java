package ir.co.pna.exchange.service.judge;

import ir.co.pna.exchange.dao.judge.JudgeDAO;
import ir.co.pna.exchange.entity.Judge;
import ir.co.pna.exchange.entity.NormalContract;
import ir.co.pna.exchange.entity.Subcontract;
import ir.co.pna.exchange.exception.MyEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class JudgeServiceImpl implements JudgeService {

    private JudgeDAO judgeDAO;

    @Autowired
    public JudgeServiceImpl(@Qualifier("judgeDAOHibernateImpl") JudgeDAO theJudgeDAO) {
        judgeDAO = theJudgeDAO;
    }

    @Override
    @Transactional
    public List<Judge> findAll() {
        return judgeDAO.findAll();
    }

    @Override
    @Transactional
    public Judge findById(String judgeId) {
        Judge theJudge = judgeDAO.findById(judgeId);

        if (theJudge == null) {
            throw new MyEntityNotFoundException("Judge id not found - " + judgeId);
        }
        return theJudge;
    }

    @Override
    @Transactional
    public String save(Judge Judge) {
        return judgeDAO.save(Judge);
    }


    @Override
    @Transactional
    public List<NormalContract> getJudgedNormalContracts(String judgeId) {
        return judgeDAO.getJudgedNormalContracts(judgeId);
    }

    @Override
    @Transactional
    public List<NormalContract> getNotJudgedNormalContracts(String judgeId) {
        return judgeDAO.getNotJudgedNormalContracts(judgeId);
    }

    @Override
    @Transactional
    public NormalContract getJudgedNormalContract(String judgeId, int normalContractId) {
        return judgeDAO.getJudgedNormalContract(judgeId, normalContractId);
    }

    @Override
    @Transactional
    public NormalContract getNotJudgedNormalContract(String judgeId, int normalContractId) {
        return judgeDAO.getNotJudgedNormalContract(judgeId, normalContractId);
    }

    @Override
    @Transactional
    public List<Subcontract> getJudgedNormalContractSubcontracts(String judgeId, int normalContractId){
        return judgeDAO.getJudgedNormalContractSubcontracts(judgeId, normalContractId);
    }

    @Override
    @Transactional
    public List<Subcontract> getNotJudgedNormalContractJudgedSubcontracts(String judgeId, int normalContractId){
        return judgeDAO.getNotJudgedNormalContractJudgedSubcontracts(judgeId, normalContractId);
    }

    @Override
    @Transactional
    public List<Subcontract> getNotJudgedNormalContractNotJudgedSubcontracts(String judgeId, int normalContractId){
        return judgeDAO.getNotJudgedNormalContractNotJudgedSubcontracts(judgeId, normalContractId);
    }

    @Override
    @Transactional
    public Subcontract getNormalContractSubcontract(String judgeId, int normalContractId, int subcontractId){
        return judgeDAO.getNormalContractSubcontract(judgeId, normalContractId, subcontractId);
    }

}






