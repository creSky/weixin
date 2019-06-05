/**
 * 2018年10月29日
 * 
 */
package weixin.domain;

import java.util.Date;

/**
 * @author Administrator
 * 结账日志保存
 *
 */
public class WxInterfaceLogDomain {
	private String url;
	private String param;
	private String returnFlag;
	private Date jzDate;
	private String transactionId;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getReturnFlag() {
		return returnFlag;
	}
	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
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
	
}
