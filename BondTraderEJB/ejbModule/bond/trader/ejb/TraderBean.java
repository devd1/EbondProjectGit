package bond.trader.ejb;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import bond.trader.jpa.Ebonddata;


/**
 * Session Bean implementation class TraderBean
 */
@Stateless
@Local(TraderBeanLocal.class)
@Remote(TraderBeanRemote.class)
public class TraderBean implements TraderBeanRemote, TraderBeanLocal {

	@PersistenceContext(name="BondTraderJPA-PU")
    private EntityManager em1;
	
	/**
     * Default constructor. 
     */
    public TraderBean() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public List<Ebonddata> viewAllbonds() {
		String sql1 = "SELECT p FROM Ebonddata AS p";
//        System.out.println(sql1);
        TypedQuery<Ebonddata> query1 = em1.createQuery(sql1, Ebonddata.class);

        List<Ebonddata> lb1 =  query1.getResultList();
        
		return lb1;
	}
	
	public List<Ebonddata> viewSpecificbonds(String in) {
		String sql2 = "SELECT p FROM Ebonddata AS p where p.currency= \'" + in + "\'";
		
//        System.out.println(sql2);
        TypedQuery<Ebonddata> query2 = em1.createQuery(sql2, Ebonddata.class);

        List<Ebonddata> lb2 =  query2.getResultList();
       
		return lb2;
	}
	
	public List<Ebonddata> viewSpecificbonds2(String in) {
		String sql3 = "SELECT p FROM Ebonddata AS p where p.organization= \'" + in + "\'";
		
//        System.out.println(sql3);
        TypedQuery<Ebonddata> query3 = em1.createQuery(sql3, Ebonddata.class);

        List<Ebonddata> lb3 =  query3.getResultList();
        
		return lb3;
	}
	
	public List<Ebonddata> filteredBond(List<String> filterParameter){
		String sector,currency,creditRating, queryForRating, queryForCouponpercent;
		int couponpercent;
//		sector = filterParameter.get(0);
		switch(Integer.parseInt(filterParameter.get(0)))
		{			
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
//		currency = filterParameter.get(1);

		switch(Integer.parseInt(filterParameter.get(1)))
		{
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
		
		
//		creditRating = filterParameter.get(2);
		switch(Integer.parseInt(filterParameter.get(2)))
		{
		
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

//		couponpercent = Integer.parseInt((filterParameter.get(3)));
		
		switch(Integer.parseInt((filterParameter.get(3))))
		{
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
			queryForCouponpercent = ">= \'5\'";
			break;
			
		}
		
		if (creditRating.equals("%")) {
			queryForRating ="like \'%\'";
		}
		else{
			queryForRating = " = \'"+creditRating+"\'";
		}
		
		
		String sql4 = "SELECT p FROM Ebonddata AS p where p.sector like \'" + sector + "\'"+ "and p.currency like \'" + currency + "\'"+"and p.creditRating " + queryForRating + "and p.couponPercent "+queryForCouponpercent;
		
		System.out.println(sql4);
        TypedQuery<Ebonddata> query4 = em1.createQuery(sql4, Ebonddata.class);

        List<Ebonddata> lb4 =  query4.getResultList();
        
		return lb4;
		
	}
	
}
