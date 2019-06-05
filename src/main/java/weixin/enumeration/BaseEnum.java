/**
 * 2018年12月28日
 * 
 */
package weixin.enumeration;

import weixin.config.Global;

/**
 * <p>
 * Title: TemplateEnum.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年12月28日
 * @remark Code is far away from bugs with the god animal protecting
 */
public enum BaseEnum {
	Template1("1",  Global.getConfig("master.templateNo1"),"wxTemplateDeal"), 
	Template2("2",  Global.getConfig("master.templateNo2"),"wxTemplateDeal"), 
	Template3("3",  Global.getConfig("master.templateNo3"),"messageStatusDeal");

	// 防止字段值被修改，增加的字段也统一final表示常量
	private final String key;
	private final String value;
	//接口处理类
	private final String interFaceClass;
	

	private BaseEnum(String key, String value,String interFaceClass) {
		this.key = key;
		this.value = value;
		this.interFaceClass=interFaceClass;
	}

	// 根据key获取枚举
	public static BaseEnum getEnumByKey(String key) {
		if (null == key) {
			return null;
		}
		for (BaseEnum temp : BaseEnum.values()) {
			if (temp.getKey().equals(key)) {
				return temp;
			}
		}
		return null;
	}
	

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public String getInterFaceClass() {
		return interFaceClass;
	}
	
	

	
}
