import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;

import bond.trader.ejb.TraderBeanRemote;
import bond.trader.jpa.Ebonddata;
import bond.trader.ejb.TraderBeanLocal;

public class MainTrade {
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {

			// Create Properties for JNDI InitialContext.
			System.out.println("check0");
			Properties prop = new Properties();
			prop.put(Context.INITIAL_CONTEXT_FACTORY,
					org.jboss.naming.remote.client.InitialContextFactory.class.getName());
			prop.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
			prop.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
			prop.put("jboss.naming.client.ejb.context", true);

			// Create the JNDI InitialContext.
			System.out.println("check1");
			Context context = new InitialContext(prop);

			// Formulate the full JNDI name for the Diary session bean.
			// String appName = "EBondTrader";
			// String moduleName = "EBondTraderEJB";
			// String beanName = "EBondTraderBean";
			// String packageName = "ebond.trader.ejb";
			// String className = "EBondTraderBeanRemote";
			// String fullName =
			// "EBondTrader/EBondTraderEJB/EBondTraderBean!ebond.trader.ejb.EBondTraderBeanRemote";
			System.out.println("check2");
			//
			// // Lookup the bean using the full JNDI name.
			// String fullJndiName = String.format("%s/%s/%s!%s.%s", appName,
			// moduleName, beanName, packageName, className);
			TraderBeanRemote bean = (TraderBeanRemote) context
					.lookup("BondTrader/BondTraderEJB/TraderBean!bond.trader.ejb.TraderBeanRemote");

			// System.out.println("check3");
			//
			// List<Ebonddata> l = bean.viewAllbonds();
			// System.out.println("check4");
			//
			// randomMethod(l);
			// System.out.println("check6");
			//
			List<Ebonddata> l2 = bean.viewSpecificbonds("Yen");
			System.out.println("check4");

			randomMethod(l2);
			System.out.println("check6");

			Scanner sc = new Scanner(System.in);
			System.out.println("Enter organisation:");
			String org = sc.nextLine();
			sc.close();
			List<Ebonddata> l3 = bean.viewSpecificbonds2(org);
			System.out.println("check4");

			randomMethod(l3);
			System.out.println("check6");

			// bean.addCategoriesAndProducts();
			//
			// List<Product> products = bean.getAllProducts();
			// displayProducts("All products", products);
			//
			// products = bean.getProductsByName("jersey");
			// displayProducts("Products by name", products);
			//
			// products = bean.getProductsInCategory("Sports");
			// displayProducts("Products in category", products);
			//
			// bean.increaseAllPrices(10);
			// products = bean.getAllProducts();
			// displayProducts("All products after 10.00 price increase",
			// products);

		} catch (Exception ex) {
			System.out.println("Exception: " + ex.getMessage());
			ex.printStackTrace();
		}

	}

	public static void randomMethod(List<Ebonddata> b) {
		for (Ebonddata e : b) {
			System.out.println(e.getId() + " " + e.getCurrency() + " " + e.getOrganization() + " " + e.getSector() + " "
					+ e.getIssuerName() + " " + e.getIsin() + " " + e.getSettlementDate());
		}
	}

	/*
	 * (non-Java-doc)
	 * 
	 * @see java.lang.Object#Object()
	 */
	// public Main() {
	// super();
	// }

}