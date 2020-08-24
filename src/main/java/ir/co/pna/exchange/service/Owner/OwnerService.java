package ir.co.pna.exchange.service.Owner;

import ir.co.pna.exchange.entity.Judge;
import ir.co.pna.exchange.entity.Owner;

import java.util.List;


public interface OwnerService {

	public List<Owner> findAll();

	public Owner findById(long id);

	public long save(Owner owner);

	public void deleteById(long id);

}
