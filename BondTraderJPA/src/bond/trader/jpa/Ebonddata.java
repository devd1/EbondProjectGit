package bond.trader.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the ebonddata database table.
 * 
 */
@Entity
//@NamedQuery(name="Ebonddata.findAll", query="SELECT e FROM Ebonddata e")
@Table(name="ebonddata")
public class Ebonddata implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private BigDecimal couponPercent;

	private String couponPeriod;

	private String creditRating;

	private String currency;

	private BigDecimal faceValue;

	private BigDecimal high;

	private String isin;

	private String issueDate;

	private String issuerName;

	private BigDecimal low;

	private String maturity;

	private String organization;

	@Column(name="`Piece Size`")
	private BigDecimal piece_Size;

	private BigDecimal priceChange;

	private String sector;

	private String settlementDate;

	private BigDecimal yield;

	public Ebonddata() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getCreditRating() {
		return this.creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getFaceValue() {
		return this.faceValue;
	}

	public void setFaceValue(BigDecimal faceValue) {
		this.faceValue = faceValue;
	}

	public BigDecimal getHigh() {
		return this.high;
	}

	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	public String getIsin() {
		return this.isin;
	}

	public void setIsin(String isin) {
		this.isin = isin;
	}

	public String getIssueDate() {
		return this.issueDate;
	}

	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	public String getIssuerName() {
		return this.issuerName;
	}

	public void setIssuerName(String issuerName) {
		this.issuerName = issuerName;
	}

	public BigDecimal getLow() {
		return this.low;
	}

	public void setLow(BigDecimal low) {
		this.low = low;
	}

	public String getMaturity() {
		return this.maturity;
	}

	public void setMaturity(String maturity) {
		this.maturity = maturity;
	}

	public String getOrganization() {
		return this.organization;
	}

	public void setOrganization(String organization) {
		this.organization = organization;
	}

	public BigDecimal getPiece_Size() {
		return this.piece_Size;
	}

	public void setPiece_Size(BigDecimal piece_Size) {
		this.piece_Size = piece_Size;
	}

	public BigDecimal getPriceChange() {
		return this.priceChange;
	}

	public void setPriceChange(BigDecimal priceChange) {
		this.priceChange = priceChange;
	}

	public String getSector() {
		return this.sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getSettlementDate() {
		return this.settlementDate;
	}

	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}

	public BigDecimal getYield() {
		return this.yield;
	}

	public void setYield(BigDecimal yield) {
		this.yield = yield;
	}

}