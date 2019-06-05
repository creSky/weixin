package weixin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import weixin.dao.WxBillDAO;
import weixin.domain.WxBillDomain;

/**
 * @author Administrator
 *
 */
@Service
public class WxBillService {

	@Resource
	WxBillDAO wxBillDAO;

	public int add(WxBillDomain wxBillDomain) {
		return wxBillDAO.insert(wxBillDomain);
	}

	public int update(WxBillDomain wxBillDomain) {
		return wxBillDAO.update(wxBillDomain);
	}

	public String findByKey(WxBillDomain wxBillDomain) {
		return wxBillDAO.findByKey(wxBillDomain);
	}

	public String checkMoney(String userNo, String tradeNo) {
		WxBillDomain wxBillDomain = new WxBillDomain();
		wxBillDomain.setUserNo(userNo);
		wxBillDomain.setTradeNo(tradeNo);
		return wxBillDAO.findByKey(wxBillDomain);
	}
	
	public String findByTransactionId(String userNo, String tradeNo) {
		WxBillDomain wxBillDomain = new WxBillDomain();
		wxBillDomain.setUserNo(userNo);
		wxBillDomain.setTradeNo(tradeNo);
		return wxBillDAO.findByTransactionId(wxBillDomain);
	}

}
