/**
 * 2018年12月25日
 * 
 */
package weixin.domain;

/**  
* <p>Title: TemplateData.java</p>  
* <p>Description: </p>  
* @author zjd  
* @date 2018年12月25日  
* @remark Code is far away from bugs with the god animal protecting
*/
public class TemplateData {
	 private String value;
	 private String color;
	 public TemplateData(String value,String color){
	        this.value = value;
	        this.color = color;
	 }
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	 
	 
}
