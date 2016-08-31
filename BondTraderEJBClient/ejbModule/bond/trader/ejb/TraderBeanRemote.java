package bond.trader.ejb;

import java.util.List;

import javax.ejb.Remote;

import bond.trader.jpa.Ebonddata;

@Remote
public interface TraderBeanRemote {
	
	List<Ebonddata> viewAllbonds();
	List<Ebonddata> viewSpecificbonds(String in);
	List<Ebonddata> viewSpecificbonds2(String in);
	List<Ebonddata> filteredBond(List<String> filterParameter);
//	List<Ebonddata> bookTrades(List<String> )
	

}
