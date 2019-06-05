/**
 * 2018年12月19日
 * 
 */
package weixin.controller;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import weixin.enumeration.BaseEnum;
import weixin.iStrategy.baseIStrategy.BaseIStrategy;
import weixin.iStrategy.context.StrategyContext;
import weixin.util.JsapiTicketUtil;

/**
 * <p>
 * Title: SendTemplateMessage.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年12月19日
 * @remark Code is far away from bugs with the god animal protecting
 * 
 *         微信发送模板消息
 */
@Controller
@RequestMapping("/message")
public class MessageController {
	
	//注入实现类
	@SuppressWarnings("rawtypes")
	@Autowired
	 private Map<String,BaseIStrategy> baseIStrategyImpl;
	/**
	 * 发送模板消息
	 * 
	 * <p>
	 * Title: sendTemplateMessage
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param misTemplateDomain
	 * @throws IOException
	 */
	@RequestMapping(value = "/sendTemplateMessage.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public int sendTemplateMessage(String templateNo) throws Exception {
		// 获取access token
		String accessToken = JsapiTicketUtil.getAccessToken();
		//取出注入的实现类
		@SuppressWarnings("unchecked")
		BaseIStrategy<Object> baseIStrategy=baseIStrategyImpl.get(BaseEnum.getEnumByKey(templateNo).getInterFaceClass());
		
		StrategyContext strategyContext=new StrategyContext(baseIStrategy);
		
		return strategyContext.sendMsg(templateNo, accessToken);
		
	}




}
