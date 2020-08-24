package ir.co.pna.exchange.service.Judge;

import ir.co.pna.exchange.dao.Judge.JudgeDAO;
import ir.co.pna.exchange.entity.Judge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class JudgeServiceImpl implements JudgeService {

	private JudgeDAO judgeDAO;
	
	@Autowired
	public JudgeServiceImpl(@Qualifier("judgeDAOJpaImpl") JudgeDAO theJudgeDAO) {
		judgeDAO = theJudgeDAO;
	}
	
	@Override
	@Transactional
	public List<Judge> findAll() {
		return judgeDAO.findAll();
	}

	@Override
	@Transactional
	public Judge findById(long theId) {
		return judgeDAO.findById(theId);
	}

	@Override
	@Transactional
	public long save(Judge Judge) {
		return judgeDAO.save(Judge);
	}

	@Override
	@Transactional
	public void deleteById(long theId) {
		judgeDAO.deleteById(theId);
	}

}






