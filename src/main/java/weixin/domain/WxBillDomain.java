/**
 * 2018年9月26日
 * 
 */
package weixin.domain;

import java.util.Date;

/**
 * @author Administrator
 * 微信支付
 */
public class WxBillDomain {
	private String userNo;
	private String tradeNo;
	private String money;
	private Character zfFlag;
	private Date zfDate;
	private Character jzFlag;
	private Date jzDate;
	private String transactionId;
	private String mon;
	private Date prePayDate;
	public String getUserNo() {
		return userNo;
	}
	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}
	public String getTradeNo() {
		return tradeNo;
	}
	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Character getZfFlag() {
		return zfFlag;
	}
	public void setZfFlag(Character zfFlag) {
		this.zfFlag = zfFlag;
	}
	public Date getZfDate() {
		return zfDate;
	}
	public void setZfDate(Date zfDate) {
		this.zfDate = zfDate;
	}
	public Character getJzFlag() {
		return jzFlag;
	}
	public void setJzFlag(Character jzFlag) {
		this.jzFlag = jzFlag;
	}
	public Date getJzDate() {
		return jzDate;
	}
	public void setJzDate(Date jzDate) {
		this.jzDate = jzDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getMon() {
		return mon;
	}
	public void setMon(String mon) {
		this.mon = mon;
	}
	/**
	 * @return the prePayDate
	 */
	public Date getPrePayDate() {
		return prePayDate;
	}
	/**
	 * @param prePayDate the prePayDate to set
	 */
	public void setPrePayDate(Date prePayDate) {
		this.prePayDate = prePayDate;
	}
	
	
}
