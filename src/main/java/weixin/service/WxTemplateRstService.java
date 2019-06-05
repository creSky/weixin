package weixin.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import weixin.dao.WxTemplateRstDAO;
import weixin.domain.WxTemplateRstDomain;

/**
 * @author Administrator
 *
 */
@Service
public class WxTemplateRstService {

	@Resource
	WxTemplateRstDAO wxTemplateRstDAO;

	public int insert(WxTemplateRstDomain wxTemplateRstDomain) {
		return wxTemplateRstDAO.insert(wxTemplateRstDomain);
	}

	//保存返回记录
	public void insertReturnData(String returnMsg,String openId,String templateNo) {
		// 保存本地记录
		WxTemplateRstDomain wxTemplateRstDomain = new WxTemplateRstDomain();
		wxTemplateRstDomain.setErrCode(JSONObject.parseObject(returnMsg).getString("errcode"));
		wxTemplateRstDomain.setErrMsg(JSONObject.parseObject(returnMsg).getString("errmsg"));
		wxTemplateRstDomain.setOpenId(openId);
		wxTemplateRstDomain.setTemplateNo(templateNo);
		wxTemplateRstDomain.setResultDate(new Date());
		wxTemplateRstDAO.insert(wxTemplateRstDomain);
	}
	
	

}
