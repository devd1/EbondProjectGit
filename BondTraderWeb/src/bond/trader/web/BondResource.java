package bond.trader.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import bond.trader.ejb.TraderBeanLocal;
import bond.trader.jpa.Ebonddata;
import bond.trader.jpa.Userbonddata;

@Path("/bond007")
public class BondResource {
	public static int testInt = 5;
	public static List<String> newList = new ArrayList<String>();

	TraderBeanLocal bean1;

	public BondResource() {
		try {
			Context context = new InitialContext();
			bean1 = (TraderBeanLocal) context
					.lookup("java:global/BondTrader/BondTraderEJB/TraderBean!bond.trader.ejb.TraderBeanLocal");
		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	// @GET
	// @Produces("text/json")
	// @Path("/BJson")
	// public List<Ebonddata> getPersonName() {
	// // Ebonddata bonddata = new Ebonddata();
	// List<Ebonddata> l2 = bean1.viewSpecificbonds("Yen");
	// return l2;
	// }

	@GET
	@Produces("text/json")
	@Path("/BJson")
	public List<Ebonddata> getPersonName() {
		String stringInput1 = "5_5_5_2";
		List<String> filterInput = (Arrays.asList(stringInput1.split("_")));

		List<Ebonddata> filterBondData = bean1.filteredBond(filterInput);
		return filterBondData;

	}

	@GET
	//@Consumes("text/json")
	@Produces("text/json")
	@Path("/filter")
	public List<Ebonddata> putFilterParameters(@QueryParam("stringInput") String stringInput) {
		List<String> filterInput = (Arrays.asList(stringInput.split("_")));

		List<Ebonddata> filterBondData = bean1.filteredBond(filterInput);
		return filterBondData;

	}
	
	@GET
	//@Consumes("text/json")
	@Produces("text/json")
	@Path("/calc")
	public List<Double> calculatorApp(@QueryParam("calcInput") String calcInput) {
		System.out.println("Yo devd");
		List<String> filterInput = (Arrays.asList(calcInput.split("_")));
		System.out.println("yo chandy");
		System.out.println(calcInput);

		List<Double> calculateBondData = bean1.calculatorApplicationProgramme(filterInput);
		System.out.println(calculateBondData);
		return calculateBondData;

	}
	
//	@GET
//	//@Consumes("text/json")
//	@Produces("text/json")
//	@Path("/trade")
//	public void placeTrade(@QueryParam("tradeInput") String tradeInput) {
//		System.out.println("Yo devd");
//		List<String> filterInput = (Arrays.asList(tradeInput.split("_")));
//		System.out.println("yo chandy");
//		System.out.println(tradeInput);
//
//		List<Double> calculateBondData = bean1.calculatorApplicationProgramme(filterInput);
//		System.out.println(calculateBondData);
//		return calculateBondData;

	}
	
	

}

// import org.json.simple.JSONObject;
