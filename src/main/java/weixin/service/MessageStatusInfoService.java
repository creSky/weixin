package weixin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import weixin.dao.MessageStatusInfoDAO;
import weixin.domain.MessageStatusInfoDomain;

/**
 * @author Administrator
 *
 */
@Service
public class MessageStatusInfoService {

	@Resource
	MessageStatusInfoDAO messageStatusInfoDAO;

	public List<MessageStatusInfoDomain> findByTemplateNo(String templateNo) {
		return messageStatusInfoDAO.findByTemplateNo(templateNo);
	}
	
	
	public void delete(MessageStatusInfoDomain messageStatusInfoDomain) {
		
		messageStatusInfoDAO.delete(messageStatusInfoDomain);
		 
	}

	public void update(MessageStatusInfoDomain messageStatusInfoDomain) {
		
		messageStatusInfoDAO.update(messageStatusInfoDomain);
		 
	}

}
