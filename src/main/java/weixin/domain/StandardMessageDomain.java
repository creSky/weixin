/**
 * 2019年6月5日
 * 
 */
package weixin.domain;

/**  
* <p>Title: StandardMessageDomain.java</p>  
* <p>Description: </p>  
* @author zjd  
* @date 2019年6月5日  
* @remark Code is far away from bugs with the god animal protecting
*/
public class StandardMessageDomain {
	//数据库模板发送的批次号
	private String templateNo;
	//模板对应的Id
	private String templateId;
	public String getTemplateNo() {
		return templateNo;
	}
	public void setTemplateNo(String templateNo) {
		this.templateNo = templateNo;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	
	
}
