/**
 * 2018年12月26日
 * 
 */
package weixin.domain;

import java.util.Date;

/**
 * <p>
 * Title: WxTemplateRstDomain.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年12月26日
 * @remark Code is far away from bugs with the god animal protecting
 */
public class WxTemplateRstDomain {
	private String openId;
	private String errCode;
	private String errMsg;
	private Date resultDate;
	private String templateNo;
	public String getTemplateNo() {
		return templateNo;
	}
	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public Date getResultDate() {
		return resultDate;
	}
	public void setResultDate(Date resultDate) {
		this.resultDate = resultDate;
	}

	

}
