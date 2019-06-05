/**
 * 2018年10月29日
 * 
 */
package weixin.domain;

import java.util.Date;

/**
 * @author Administrator
 *
 */
public class WxPrePayLogDomain {
	// 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
	private String prepayId;
	// 微信返回的随机字符串
	private String nonceStr;
	// 加密方式
	private String signType;
	// 调用接口提交的应用ID
	private String appId;
	// 时间戳 保留到秒
	private String timestamp;
	// 根据时间戳和字符串 sha1加密生成的签名
	private String signature;
	// 返回状态码
	private String returnCode;
	// 返回信息
	private String returnMsg;
	//户号
	private String userNo;
	//交易号
	private String tradeNo;
	//日期
	private Date zfDate;
	
	

	public Date getZfDate() {
		return zfDate;
	}

	public void setZfDate(Date zfDate) {
		this.zfDate = zfDate;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public void setNonceStr(String nonceStr) {
		this.nonceStr = nonceStr;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

}
