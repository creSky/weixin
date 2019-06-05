/**
 * 2019年5月23日
 * 
 */
package weixin.iStrategy.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import weixin.config.Global;
import weixin.domain.TemplateData;
import weixin.domain.TemplateMessage;
import weixin.domain.WxTemplateInfoDomain;
import weixin.enumeration.BaseEnum;
import weixin.iStrategy.baseIStrategy.BaseIStrategy;
import weixin.service.WxTemplateInfoService;
import weixin.service.WxTemplateRstService;
import weixin.util.PayCommonUtil;
import weixin.util.StringUtils;

/**  
* <p>Title: WxTemplateDeal.java</p>  
* <p>Description: 
*  微信余额群发停电消息实现类
* </p>  
* @author zjd  
 * @param <T>
 * @param <T>
* @date 2019年5月23日  
* @remark Code is far away from bugs with the god animal protecting
*/
@SuppressWarnings("rawtypes")
@Service
public class WxTemplateDeal implements BaseIStrategy{

	@Autowired
	private WxTemplateInfoService wxTemplateInfoService;
	
	@Autowired
	private WxTemplateRstService wxTemplateRstService;
	
	private static String sendTemplateUrl = Global.getConfig("master.sendTemplateUrl");

	/* (non-Javadoc)
	 * @see weixin.iStrategy.baseIStrategy.BaseIStrategy#findUsers()
	 */
	@Override
	public int sendMsg(String templateNo,String accessToken) {
		// TODO Auto-generated method stub
		// 获取关注公众号的用户列表 数据库取
		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

		List<WxTemplateInfoDomain> wxTemplateInfoDomains =wxTemplateInfoService.findByTemplateNo(templateNo);

		// 线程池优化速度
		for (WxTemplateInfoDomain wxTemplateInfoDomain : wxTemplateInfoDomains) {

			executorService.execute(new Runnable() {

				public void run() {
					dealMsg(accessToken, wxTemplateInfoDomain);

				}
			});

		}
		executorService.shutdown();
		return wxTemplateInfoDomains.size();
	}

	/* (non-Javadoc)
	 * @see weixin.iStrategy.baseIStrategy.BaseIStrategy#dealMsg(java.lang.String, java.lang.Object)
	 */
	@Override
	public synchronized void dealMsg(String accessToken,Object t) {
		// TODO Auto-generated method stub
		WxTemplateInfoDomain wxTemplateInfoDomain =(WxTemplateInfoDomain) t;
		String sendMessageUrl = sendTemplateUrl.replace("ACCESS_TOKEN", accessToken);

		TemplateMessage templateMessage = new TemplateMessage();

		templateMessage.setTouser(wxTemplateInfoDomain.getOpenId());

		Map<String, TemplateData> templateData = new HashMap<>();

		// 字体颜色
		String rgb = StringUtils.getRGB();

		// 枚举 给根据出入的模板序号 得到相应的模板id
		templateMessage.setTemplate_id(BaseEnum.getEnumByKey(wxTemplateInfoDomain.getTemplateNo()).getValue());

		templateData.put("first", new TemplateData(wxTemplateInfoDomain.getFirst(), rgb));
		templateData.put("keyword1", new TemplateData(wxTemplateInfoDomain.getKeyword1(), rgb));
		templateData.put("keyword2", new TemplateData(wxTemplateInfoDomain.getKeyword2(), rgb));
		templateData.put("keyword3", new TemplateData(wxTemplateInfoDomain.getKeyword3(), rgb));
		templateData.put("keyword4", new TemplateData(wxTemplateInfoDomain.getKeyword4(), rgb));
		templateData.put("keyword5", new TemplateData(wxTemplateInfoDomain.getKeyword5(), rgb));
		templateData.put("remark", new TemplateData(wxTemplateInfoDomain.getRemark(), rgb));

		templateMessage.setData(templateData);

		String sendJson = JSONObject.toJSON(templateMessage).toString();

		String templateRst = PayCommonUtil.httpsRequest(sendMessageUrl, "POST", sendJson);

		// 删除记录
		wxTemplateInfoService.delete(wxTemplateInfoDomain);

		// 保存本地记录
		wxTemplateRstService.insertReturnData(templateRst, wxTemplateInfoDomain.getOpenId(),wxTemplateInfoDomain.getTemplateNo());
	}


}
