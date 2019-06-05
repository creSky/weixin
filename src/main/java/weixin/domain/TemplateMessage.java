/**
 * 2018年12月25日
 * 
 */
package weixin.domain;

import java.util.Map;

/**  
* <p>Title: TemplateMessage.java</p>  
* <p>Description: </p>  
* @author zjd  
* @date 2018年12月25日  
* @remark Code is far away from bugs with the god animal protecting
*/
public class TemplateMessage {
	  private String touser; //用户OpenID
	    private String template_id; //模板消息ID
	    private String url; //URL置空，在发送后，点模板消息进入一个空白页面（ios），或无法点击（android）。
	    private String topcolor; //标题颜色
	    private Map<String, TemplateData> data; //模板详细信息
		public String getTouser() {
			return touser;
		}
		public void setTouser(String touser) {
			this.touser = touser;
		}
		public String getTemplate_id() {
			return template_id;
		}
		public void setTemplate_id(String template_id) {
			this.template_id = template_id;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getTopcolor() {
			return topcolor;
		}
		public void setTopcolor(String topcolor) {
			this.topcolor = topcolor;
		}
		public Map<String, TemplateData> getData() {
			return data;
		}
		public void setData(Map<String, TemplateData> data) {
			this.data = data;
		}


}