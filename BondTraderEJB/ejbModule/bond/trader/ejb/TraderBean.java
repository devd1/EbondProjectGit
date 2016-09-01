package bond.trader.ejb;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.swing.table.TableCellRenderer;

import org.jboss.security.auth.spi.Users.User;


import bond.trader.jpa.Ebonddata;
import bond.trader.jpa.Userbonddata;
import javafx.scene.chart.PieChart.Data;

/**
 * Session Bean implementation class TraderBean
 */
@Stateless
@Local(TraderBeanLocal.class)
@Remote(TraderBeanRemote.class)
public class TraderBean implements TraderBeanRemote, TraderBeanLocal {

	@PersistenceContext(name = "BondTraderJPA-PU")
	private EntityManager em1;

	/**
	 * Default constructor.
	 */
	public TraderBean() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Userbonddata> viewAllbonds() {
		String sql1 = "SELECT p FROM Userbonddata AS p";
		// System.out.println(sql1);
		TypedQuery<Userbonddata> query1 = em1.createQuery(sql1, Userbonddata.class);

		List<Userbonddata> lb1 = query1.getResultList();

		return lb1;
	}

	public List<Ebonddata> viewSpecificbonds(String in) {
		String sql2 = "SELECT p FROM Ebonddata AS p where p.currency= \'" + in + "\'";

		// System.out.println(sql2);
		TypedQuery<Ebonddata> query2 = em1.createQuery(sql2, Ebonddata.class);

		List<Ebonddata> lb2 = query2.getResultList();

		return lb2;
	}

	public List<Ebonddata> viewSpecificbonds2(String in) {
		String sql3 = "SELECT p FROM Ebonddata AS p where p.organization= \'" + in + "\'";

		// System.out.println(sql3);
		TypedQuery<Ebonddata> query3 = em1.createQuery(sql3, Ebonddata.class);

		List<Ebonddata> lb3 = query3.getResultList();

		return lb3;
	}

	public List<Ebonddata> filteredBond(List<String> filterParameter) {
		String sector, currency, creditRating, queryForRating, queryForCouponpercent;
		// int couponpercent;
		// sector = filterParameter.get(0);
		switch (Integer.parseInt(filterParameter.get(0))) {
		case 1:
			sector = "Treasury Bills";
			break;

		case 2:
			sector = "Agriculture";
			break;
		case 3:
			sector = "Energy";
			break;
		case 4:
			sector = "Real Estate";
			break;
		default:
			sector = "%";
			break;

		}
		// currency = filterParameter.get(1);

		switch (Integer.parseInt(filterParameter.get(1))) {
		case 1:
			currency = "USD";
			break;

		case 2:
			currency = "Rupee";
			break;

		case 3:
			currency = "Yen";
			break;
		default:
			currency = "%";
			break;
		}

		// creditRating = filterParameter.get(2);
		switch (Integer.parseInt(filterParameter.get(2))) {

		case 1:
			creditRating = "A";
			break;

		case 2:
			creditRating = "AA+";
			break;

		case 3:
			creditRating = "AAA+";
			break;
		default:
			creditRating = "%";
			break;
		}

		// couponpercent = Integer.parseInt((filterParameter.get(3)));

		switch (Integer.parseInt((filterParameter.get(3)))) {
		case 0:
			queryForCouponpercent = ">= \'5\'";
			break;
		case 1:
			queryForCouponpercent = "between \'4\' and \'5\'";
			break;

		case 2:
			queryForCouponpercent = "between \'3\' and \'4\'";
			break;
		case 3:
			queryForCouponpercent = "<= \'3\'";
			break;

		default:
			queryForCouponpercent = "like \'%\'";
			break;

		}

		if (creditRating.equals("%")) {
			queryForRating = "like \'%\'";
		} else {
			queryForRating = " = \'" + creditRating + "\'";
		}

		String sql4 = "SELECT p FROM Ebonddata AS p where p.sector like \'" + sector + "\'" + "and p.currency like \'"
				+ currency + "\'" + "and p.creditRating " + queryForRating + "and p.couponPercent "
				+ queryForCouponpercent;

		System.out.println(sql4);
		TypedQuery<Ebonddata> query4 = em1.createQuery(sql4, Ebonddata.class);

		List<Ebonddata> lb4 = query4.getResultList();

		return lb4;

	}

	public List<Userbonddata> userBondSelection(String userSpcificData) {
		Userbonddata userData = new Userbonddata();
		List<Userbonddata> userDataList;
		userDataList = null;

		return userDataList;
	}

	// ---------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------

	public static int getDaysBetween(Date previous, Date recent) {
		return (int) (Math.abs(recent.getTime() - previous.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static int calculateNoOfDaysTillNextCoupon(Date tradeDate, Date nextCouponDate) {
		return getDaysBetween(tradeDate, nextCouponDate);
	}

	public static int calculateNoOfDaysSinceLastCoupon(Date tradeDate, Date lastCouponDate) {
		return getDaysBetween(lastCouponDate, tradeDate);
	}

	public static int calculateCouponPeriodInDays(Date nextCouponDate, Date lastCouponDate) {
		return getDaysBetween(lastCouponDate, nextCouponDate);
	}

	public static int calculateMaturityPeriodinYears(Date startDate, Date maturityDate) {
		return Math.floorDiv(getDaysBetween(startDate, maturityDate), 360);
	}

	public static int getCouponFrequncy(Ebonddata e) {
		String couponPeriod = e.getCouponPeriod();
		switch (couponPeriod.toLowerCase()) {
		case "annual":
			return 1;
		case "semiannual":
			return 2;
		case "quarter":
			return 4;
		default:
			return -1; // check for -1 for further calculation
		}
	}

	public static Date getNextCouponDate(Ebonddata e, Date tradeDate) {
		Date nextCouponDate = null;
		String maturityDate = e.getMaturity();
		//int frequency = getCouponFrequncy(e);

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(Ebonddata.getStringFromDate(tradeDate));
			d2 = format.parse(maturityDate);
			long days = (int) TimeUnit.DAYS.convert(d2.getTime() - d1.getTime(), TimeUnit.MILLISECONDS);
			int yr = (int)days/365;
			long time = Ebonddata.getDateFromString(maturityDate).getTime();
			
			String y = new SimpleDateFormat("YYYY").format(new Date(time));
			String m = new SimpleDateFormat("MM").format(new Date(time));
			String d = new SimpleDateFormat("dd").format(new Date(time));
			int year = Integer.parseInt(y) - yr;
			y = Integer.toString(year);
			nextCouponDate = Ebonddata.getDateFromString(d + "/" + m + "/" + y);
			
	} catch (Exception ex) {
			ex.printStackTrace();
		}

		
		return nextCouponDate;
	}

	public static Date getLastCouponDate(Ebonddata e, Date tradeDate) {
		Date lastCouponDate = null;
		String maturityDate = e.getMaturity();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		Date d1 = null;
		Date d2 = null;

		try {
			d1 = format.parse(Ebonddata.getStringFromDate(tradeDate));
			d2 = format.parse(maturityDate);
			long days = (int) TimeUnit.DAYS.convert(d2.getTime() - d1.getTime(), TimeUnit.MILLISECONDS);
			int yr = (int)days/365;
			long time = Ebonddata.getDateFromString(maturityDate).getTime();
			
			String y = new SimpleDateFormat("YYYY").format(new Date(time));
			String m = new SimpleDateFormat("MM").format(new Date(time));
			String d = new SimpleDateFormat("dd").format(new Date(time));
			int year = Integer.parseInt(y) - yr -1;
			y = Integer.toString(year);
			lastCouponDate = Ebonddata.getDateFromString(d + "/" + m + "/" + y);
			
	} catch (Exception ex) {
			ex.printStackTrace();
		}
		return lastCouponDate;
	}
	// bond dependent values and couponPercent in percentage
	public static double calculateCouponPay(Ebonddata e) {
		double couponRate = e.getCouponPercent().doubleValue();
		double FACEVALUE = e.getFaceValue().doubleValue();
		return (FACEVALUE * ((couponRate / 100.0)));

	}

	public static double calculateAccruedInterest(Date tradeDate, Ebonddata e) {

		Date lastCouponDate = getLastCouponDate(e, tradeDate);
		Date nextCouponDate = getNextCouponDate(e, tradeDate);

		int couponPeriod = calculateCouponPeriodInDays(nextCouponDate, lastCouponDate);

		return (calculateCouponPay(e) * ((double) getDaysBetween(lastCouponDate, tradeDate) / (double) couponPeriod));

	}

	public static double calculateCleanPriceFromYield(Date tradeDate, Ebonddata e) {

		Date lastCouponDate = getLastCouponDate(e, tradeDate);
		int maturityPeriod = calculateMaturityPeriodinYears(lastCouponDate, e.getMaturityDateFormat());
		int frequency = getCouponFrequncy(e);
		int noOfIterations = maturityPeriod * frequency;
		double FACEVALUE = e.getFaceValueDouble();
		double couponPay = calculateCouponPay(e);
		double yield = e.getYieldDouble();
		yield = yield / 100;

		double dirtyPrice = (couponPay * ((1 - (1 / Math.pow((1 + yield), (noOfIterations)))) / (yield)))
				+ (FACEVALUE / (Math.pow((1 + yield), (noOfIterations))));

		double cleanPrice = dirtyPrice - calculateAccruedInterest(tradeDate, e);
		return cleanPrice;

	}

	public static double calculateCleanPriceFromYield(double yield, Date tradeDate, Ebonddata e) {

		Date lastCouponDate = getLastCouponDate(e, tradeDate);
		int maturityPeriod = calculateMaturityPeriodinYears(lastCouponDate, e.getMaturityDateFormat());
		int frequency = getCouponFrequncy(e);
		int noOfIterations = maturityPeriod * frequency;
		double FACEVALUE = e.getFaceValueDouble();
		double couponPay = calculateCouponPay(e);
		yield = yield / 100;

		double dirtyPrice = (couponPay * ((1 - (1 / Math.pow((1 + yield), (noOfIterations)))) / (yield)))
				+ (FACEVALUE / (Math.pow((1 + yield), (noOfIterations))));

		double cleanPrice = dirtyPrice - calculateAccruedInterest(tradeDate, e);
		return cleanPrice;

	}

	public static double calculateDirtyPriceFromYield(Date tradeDate, Ebonddata e) {

		Date lastCouponDate = getLastCouponDate(e, tradeDate);
		int maturityPeriod = calculateMaturityPeriodinYears(lastCouponDate, e.getMaturityDateFormat());
		int frequency = getCouponFrequncy(e);
		int noOfIterations = maturityPeriod * frequency;
		double FACEVALUE = e.getFaceValueDouble();
		double couponPay = calculateCouponPay(e);
		double yield = e.getYieldDouble();
		yield = yield / 100;

		double dirtyPrice = (couponPay * ((1 - (1 / Math.pow((1 + yield), (noOfIterations)))) / (yield)))
				+ (FACEVALUE / (Math.pow((1 + yield), (noOfIterations))));
		return dirtyPrice;

	}

	public static double calculateDirtyPriceFromYield(double yield, Date tradeDate, Ebonddata e) {

		Date lastCouponDate = getLastCouponDate(e, tradeDate);
		int maturityPeriod = calculateMaturityPeriodinYears(lastCouponDate, e.getMaturityDateFormat());
		int frequency = getCouponFrequncy(e);
		int noOfIterations = maturityPeriod * frequency;
		double FACEVALUE = e.getFaceValueDouble();
		double couponPay = calculateCouponPay(e);
		yield = yield / 100;

		double dirtyPrice = (couponPay * ((1 - (1 / Math.pow((1 + yield), (noOfIterations)))) / (yield)))
				+ (FACEVALUE / (Math.pow((1 + yield), (noOfIterations))));
		return dirtyPrice;

	}

	public static double calculateYTMfromCleanPrice(Ebonddata e, Date tradeDate) {

		double lastTradePrice = e.getPiece_SizeDouble();

		double tolerance = 0.0001;
		double estimatedCleanPrice = 0;

		double lowerYield = 0.0001;
		double upperYield = 100;
		double estimatedYield = (lowerYield + upperYield) / 2;
		int iterations = 0 ;
		int limit = 100;

		while ((Math.abs(lastTradePrice - estimatedCleanPrice)) > tolerance && iterations < limit) {
			estimatedYield = (lowerYield + upperYield) / 2;
			estimatedCleanPrice = calculateCleanPriceFromYield(estimatedYield, tradeDate, e);
			if (estimatedCleanPrice > lastTradePrice) {
				lowerYield = estimatedYield;
			} else if (estimatedCleanPrice < lastTradePrice) {
				upperYield = estimatedYield;
			}
			iterations++;
		}
		return estimatedYield;
	}

	public static double calculateDirtyPrice(Date tradeDate, Ebonddata e) {

		double lastTradePrice = e.getPiece_SizeDouble();
		return (lastTradePrice + calculateAccruedInterest(tradeDate, e));

	}

	public static double calculateYTMfromCleanPrice(double cleanPrice, Ebonddata e, Date tradeDate) {

		double tolerance = 0.0001;
		double estimatedCleanPrice = 0;

		double lowerYield = 0.0001;
		double upperYield = 100;
		double estimatedYield = (lowerYield + upperYield) / 2;
		int iterations = 0 ;
		int limit = 100;


		while ((Math.abs(cleanPrice - estimatedCleanPrice)) > tolerance && iterations < limit) {
			estimatedYield = (lowerYield + upperYield) / 2;
			estimatedCleanPrice = calculateCleanPriceFromYield(estimatedYield, tradeDate, e);
			if (estimatedCleanPrice > cleanPrice) {
				lowerYield = estimatedYield;
			} else if (estimatedCleanPrice < cleanPrice) {
				upperYield = estimatedYield;
			}
			iterations++;
		}
		return estimatedYield;
	}

	public static double calculateDirtyPriceFromCleanPrice(double cleanPrice, Date tradeDate, Ebonddata e) {

		return (cleanPrice + calculateAccruedInterest(tradeDate, e));

	}

	public static double calculateYTMfromDirtyPrice(double dirtyPrice, Ebonddata e, Date tradeDate) {

		double cleanPrice = dirtyPrice - calculateAccruedInterest(tradeDate, e);
		double tolerance = 0.0001;
		double estimatedCleanPrice = 0;

		double lowerYield = 0.0001;
		double upperYield = 100;
		double estimatedYield = (lowerYield + upperYield) / 2;

		int iterations = 0;
		int limit= 100;
		while ((Math.abs(cleanPrice - estimatedCleanPrice)) > tolerance && iterations < limit) {
			estimatedYield = (lowerYield + upperYield) / 2;
			estimatedCleanPrice = calculateCleanPriceFromYield(estimatedYield, tradeDate, e);
			if (estimatedCleanPrice > cleanPrice) {
				lowerYield = estimatedYield;
			} else if (estimatedCleanPrice < cleanPrice) {
				upperYield = estimatedYield;
			}
			iterations++;
		}
		return estimatedYield;
	}

	public static double calculateCleanPriceFromDirtyPrice(double dirtyPrice, Ebonddata e, Date tradeDate) {
		return (dirtyPrice - calculateAccruedInterest(tradeDate, e));

	}

	public List<Double> calculatorApplicationProgramme(List<String> input) {
		int index = 2;
		List<Double> returnCalcData = new ArrayList<Double>();
		String stringInputDate = input.get(1);
		System.out.println(stringInputDate);
		Date inputDate = Ebonddata.getDateFromString(stringInputDate);
		System.out.println(inputDate);
		String sql4 = "SELECT p FROM Ebonddata AS p where p.isin like \'"+input.get(0)+"\'";
		System.out.println("yo purva");
		TypedQuery<Ebonddata> query4 = em1.createQuery(sql4, Ebonddata.class);
		System.out.println("check1");

//		List<Ebonddata> lb4 = query4.getResultList();
		Ebonddata choosenBond = query4.getSingleResult();
		double cleanPrice, dirtyPrice, yield;
		while (input.get(index).equals("0") && index < 5) {
			index++;
		}

		switch (index) {
		case 2:
			yield = Double.parseDouble(input.get(2));
			cleanPrice = calculateCleanPriceFromYield(yield, inputDate, choosenBond);
			dirtyPrice = calculateDirtyPriceFromYield(yield, inputDate, choosenBond);
			returnCalcData.add(yield);
			returnCalcData.add(cleanPrice);
			returnCalcData.add(dirtyPrice);
			break;
		case 3:
			System.out.println("case 3");
			cleanPrice = Double.parseDouble(input.get(3));
			System.out.println(cleanPrice);
			dirtyPrice = calculateDirtyPriceFromCleanPrice(cleanPrice, inputDate, choosenBond);

//			yield = calculateYTMfromCleanPrice(cleanPrice, choosenBond, inputDate);
			System.out.println(dirtyPrice);
//			dirtyPrice = calculateDirtyPriceFromCleanPrice(cleanPrice, inputDate, choosenBond);
			yield = calculateYTMfromCleanPrice(cleanPrice, choosenBond, inputDate);
			System.out.println(yield);

			System.out.println("c");
			returnCalcData.add(yield);
			returnCalcData.add(cleanPrice);
			returnCalcData.add(dirtyPrice);
			System.out.println(returnCalcData);
			break;

		case 4:
			System.out.println("case 4");
			dirtyPrice = Double.parseDouble(input.get(4));
			System.out.println(dirtyPrice);
//			yield = calculateYTMfromDirtyPrice(dirtyPrice, choosenBond, inputDate);
			cleanPrice = calculateCleanPriceFromDirtyPrice(dirtyPrice, choosenBond, inputDate);
			System.out.println(cleanPrice);
			yield = calculateYTMfromDirtyPrice(dirtyPrice, choosenBond, inputDate);
			System.out.println(yield);

			returnCalcData.add(yield);
			returnCalcData.add(cleanPrice);
			returnCalcData.add(dirtyPrice);
			break;

		default:
			break;
		}

		
		return returnCalcData;

	}
	
	
	
	public void addToUserBondData(List<String> tradeData){
		
		String sql4 = "SELECT p FROM Ebonddata AS p where p.isin like \'"+tradeData.get(0)+"\'";
		TypedQuery<Ebonddata> query4 = em1.createQuery(sql4, Ebonddata.class);

		Ebonddata choosenBond = query4.getSingleResult();
		Userbonddata addData = new Userbonddata();
		Double yield,cleanPrice,dirtyPrice;
		yield = Double.parseDouble(tradeData.get(3));
		cleanPrice = Double.parseDouble(tradeData.get(4));
		dirtyPrice = Double.parseDouble(tradeData.get(5));
		addData.setOrganiation(choosenBond.getOrganization());
		addData.setCurrency(choosenBond.getCurrency());
		addData.setSector(choosenBond.getSector());
		addData.setIsin(choosenBond.getIsin());
//		addData.setPrice(choosenBond.getPriceChange());
		addData.setCreditRting(choosenBond.getCreditRating());
		addData.setCouponPercent(choosenBond.getCouponPercent());
		addData.setCouponPeriod(choosenBond.getCouponPeriod());
		addData.setMaturity(choosenBond.getMaturityDateFormat());
		addData.setSettlementDate(choosenBond.getSettlementDate());
		addData.setQuantity(Integer.parseInt(tradeData.get(2)));
		List<String> dataForTrade = new ArrayList<>();
		dataForTrade.add(tradeData.get(0));
		dataForTrade.add(tradeData.get(1));
		dataForTrade.add(tradeData.get(3));
		dataForTrade.add("0");
		dataForTrade.add("0");
		List<Double> dataForYieldCleanDirtyPrice = calculatorApplicationProgramme(dataForTrade);
//		addData.setYield(BigDecimal.valueOf(dataForYieldCleanDirtyPrice.get(0)));
//		addData.setCleanPrice(BigDecimal.valueOf(dataForYieldCleanDirtyPrice.get(1)));
//		addData.setDirtyPrice(BigDecimal.valueOf(dataForYieldCleanDirtyPrice.get(2)));
		addData.setYield(BigDecimal.valueOf(yield));
		addData.setCleanPrice(BigDecimal.valueOf(cleanPrice));
		addData.setDirtyPrice(BigDecimal.valueOf(dirtyPrice));
		addData.setSettlementAmount(BigDecimal.valueOf(Integer.parseInt(tradeData.get(2))*(dataForYieldCleanDirtyPrice.get(1))));
		System.out.println("reached persist");
		em1.persist(addData);
		System.out.println("persisted");

				
	}

}
