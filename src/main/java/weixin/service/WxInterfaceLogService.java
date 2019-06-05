package weixin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import weixin.dao.WxInterfaceLogDAO;
import weixin.domain.WxInterfaceLogDomain;

/**
 * @author Administrator
 *
 */
@Service
public class WxInterfaceLogService {

	@Resource
	WxInterfaceLogDAO wxInterfaceLogDAO;

	public int add(WxInterfaceLogDomain wxInterfaceLogDomain) {
		return wxInterfaceLogDAO.insert(wxInterfaceLogDomain);
	}

}
