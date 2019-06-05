/**
 * 2018年9月26日
 * 
 */
package weixin.dao;

import java.util.List;

import weixin.domain.WxTemplateInfoDomain;

/**
 * @author Administrator
 * 模板信息
 */
public interface WxTemplateInfoDAO {
	
	public List<WxTemplateInfoDomain> findByTemplateNo(String templateNo);
	
	public void delete(WxTemplateInfoDomain t);
	
	public void update(WxTemplateInfoDomain t);


}
