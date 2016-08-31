package bond.trader.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the userbonddata database table.
 * 
 */
@Entity
//@NamedQuery(name="Userbonddata.findAll", query="SELECT u FROM Userbonddata u")
@Table(name="userbonddata")
public class Userbonddata implements Serializable {
	private static final long serialVersionUID = 1L;

//	@Id
	private int orderid;

	private BigDecimal cleanPrice;

	private BigDecimal couponPercent;

	private String couponPeriod;

	private String creditRting;

	private String currency;

	private BigDecimal dirtyPrice;

	private String isin;

	@Temporal(TemporalType.DATE)
	private Date maturity;

	private String organiation;

	private BigDecimal price;

	private int quantity;

	private String sector;

	private BigDecimal settlementAmount;

	@Temporal(TemporalType.DATE)
	private Date settlementDate;

	private BigDecimal yield;

	public Userbonddata() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getOrderid() {
		return this.orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public BigDecimal getCleanPrice() {
		return this.cleanPrice;
	}

	public void setCleanPrice(BigDecimal cleanPrice) {
		this.cleanPrice = cleanPrice;
	}

	public BigDecimal getCouponPercent() {
		return this.couponPercent;
	}

	public void setCouponPercent(BigDecimal couponPercent) {
		this.couponPercent = couponPercent;
	}

	public String getCouponPeriod() {
		return this.couponPeriod;
	}

	public void setCouponPeriod(String couponPeriod) {
		this.couponPeriod = couponPeriod;
	}

	public String getCreditRting() {
		return this.creditRting;
	}

	public void setCreditRting(String creditRting) {
		this.creditRting = creditRting;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getDirtyPrice() {
		return this.dirtyPrice;
	}

	public void setDirtyPrice(BigDecimal dirtyPrice) {
		this.dirtyPrice = dirtyPrice;
	}

	public String getIsin() {
		return this.isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public Date getMaturity() {
		return this.maturity;
	}

	public void setMaturity(Date maturity) {
		this.maturity = maturity;
	}

	public String getOrganiation() {
		return this.organiation;
	}

	public void setOrganiation(String organiation) {
		this.organiation = organiation;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public int getQuantity() {
		return this.quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getSector() {
		return this.sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public BigDecimal getSettlementAmount() {
		return this.settlementAmount;
	}

	public void setSettlementAmount(BigDecimal settlementAmount) {
		this.settlementAmount = settlementAmount;
	}

	public Date getSettlementDate() {
		return this.settlementDate;
	}

	public void setSettlementDate(Date settlementDate) {
		this.settlementDate = settlementDate;
	}

	public BigDecimal getYield() {
		return this.yield;
	}

	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}

}