package weixin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import weixin.dao.WxBillErrorDAO;
import weixin.domain.WxBillErrorDomain;

/**
 * @author Administrator
 *
 */
@Service
public class WxBillErrorService {

	@Resource
	WxBillErrorDAO wxBillErrorDAO;
	
	public int add(WxBillErrorDomain wxBillErrorDomain) {
        return wxBillErrorDAO.insert(wxBillErrorDomain);
    }
	
}
