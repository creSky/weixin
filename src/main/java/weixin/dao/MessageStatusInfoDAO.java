/**
 * 2018年9月26日
 * 
 */
package weixin.dao;

import java.util.List;

import weixin.domain.MessageStatusInfoDomain;

/**
 * @author Administrator
 * 模板信息
 */
public interface MessageStatusInfoDAO {
	
	public List<MessageStatusInfoDomain> findByTemplateNo(String templateNo);
	
	public void delete(MessageStatusInfoDomain t);
	
	public void update(MessageStatusInfoDomain t);


}
