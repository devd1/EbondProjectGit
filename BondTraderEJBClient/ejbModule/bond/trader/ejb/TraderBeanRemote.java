package bond.trader.ejb;

import java.util.List;

import javax.ejb.Remote;

import bond.trader.jpa.Ebonddata;
import bond.trader.jpa.Userbonddata;

@Remote
public interface TraderBeanRemote {
	
	List<Ebonddata> viewAllbonds();
	List<Ebonddata> viewSpecificbonds(String in);
	List<Ebonddata> viewSpecificbonds2(String in);
	List<Ebonddata> filteredBond(List<String> filterParameter);
	List<Userbonddata> userBondSelection(String userSpcificData);
	List<Double> calculatorApplicationProgramme(List<String> input);

}
