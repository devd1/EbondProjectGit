package bond.trader.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.junit.Before;
import org.junit.Test;

import bond.trader.ejb.TraderBeanRemote;
import bond.trader.jpa.Ebonddata;

public class BondTraderTest {
	
	TraderBeanRemote bean;

	@Before
	public void init() {
		// TODO Auto-generated method stub
		

		try {

			// Create Properties for JNDI InitialContext.
			Properties prop = new Properties();
			prop.put(Context.INITIAL_CONTEXT_FACTORY,
					org.jboss.naming.remote.client.InitialContextFactory.class.getName());
			prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			prop.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			prop.put("jboss.naming.client.ejb.context", true);

			// Create the JNDI InitialContext.
			Context context = new InitialContext(prop);
			 bean = (TraderBeanRemote) context
					.lookup("BondTrader/BondTraderEJB/TraderBean!bond.trader.ejb.TraderBeanRemote");
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			ex.printStackTrace();
		}
		System.out.println("traderbean created");
	}
	
	@Test
	public void testFilterBondMethod(){
		try{
			String testString="3_3_4_2";
			List<String> testFilterInput = (Arrays.asList(testString.split("_")));

			List<Ebonddata> testFilterBondData = bean.filteredBond(testFilterInput);
			String getISIN1 = testFilterBondData.get(0).getIsin();
			String getISIN2 = testFilterBondData.get(1).getIsin();
			String testISINValue1="LCRPT74176";
			String testISINValue2="EXIUZ7428";
//			assertEquals(testISINValue1,getISIN1);
			assertEquals(testISINValue1,getISIN2);
			
		}
		catch(Exception e){
			System.out.println("error");
			e.printStackTrace();
		}
	}

}
