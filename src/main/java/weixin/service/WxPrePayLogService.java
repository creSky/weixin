package weixin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import weixin.dao.WxPrePayLogDAO;
import weixin.domain.WxPrePayLogDomain;

/**
 * @author Administrator
 *
 */
@Service
public class WxPrePayLogService {

	@Resource
	WxPrePayLogDAO wxPrePayLogDAO;

	public int add(WxPrePayLogDomain wxPrePayLogDomain) {
		return wxPrePayLogDAO.insert(wxPrePayLogDomain);
	}

}
