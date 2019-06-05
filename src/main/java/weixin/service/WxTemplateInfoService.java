package weixin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import weixin.dao.WxTemplateInfoDAO;
import weixin.domain.WxTemplateInfoDomain;

/**
 * @author Administrator
 *
 */
@Service
public class WxTemplateInfoService {

	@Resource
	WxTemplateInfoDAO wxTemplateInfoDAO;

	public List<WxTemplateInfoDomain> findByTemplateNo(String templateNo) {
		return wxTemplateInfoDAO.findByTemplateNo(templateNo);
	}
	
	
	public void delete(WxTemplateInfoDomain wxTemplateInfoDomain) {
		
		 wxTemplateInfoDAO.delete(wxTemplateInfoDomain);
		 
	}

	public void update(WxTemplateInfoDomain wxTemplateInfoDomain) {
		
		 wxTemplateInfoDAO.update(wxTemplateInfoDomain);
		 
	}

}
