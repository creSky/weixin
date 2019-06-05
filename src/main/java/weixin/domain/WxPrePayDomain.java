/**
 * 2018年9月29日
 * 
 */
package weixin.domain;

import java.math.BigDecimal;

/**
 * @author Administrator
 * 预支付实体
 */
public class WxPrePayDomain {
	//暂定户号
	private String trade_no;
	//总金额
	private BigDecimal totalAmount;
	//描述
	private String description;
	//公众号id
	private String openId;
	//生成signature
	private String tokenUrl;
	//月份
	private String mon;

	public String getTrade_no() {
		return trade_no;
	}

	public void setTrade_no(String trade_no) {
		this.trade_no = trade_no;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public String getMon() {
		return mon;
	}

	public void setMon(String mon) {
		this.mon = mon;
	}

}
