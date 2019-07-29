package weixin.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom2.JDOMException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import weixin.config.Global;
import weixin.domain.WxBillDomain;
import weixin.domain.WxBillErrorDomain;
import weixin.domain.WxInterfaceLogDomain;
import weixin.domain.WxPrePayDomain;
import weixin.domain.WxPrePayLogDomain;
import weixin.service.WxBillErrorService;
import weixin.service.WxBillService;
import weixin.service.WxInterfaceLogService;
import weixin.service.WxPrePayLogService;
import weixin.util.DateUtil;
import weixin.util.JsapiTicketUtil;
import weixin.util.PayCommonUtil;
import weixin.util.Sign;

/**
 * <p>
 * Title: PayController.java
 * </p>
 * <p>
 * Description:
 * </p>
 * 
 * @author zjd
 * @date 2018年11月12日 v2
 * @remark Code is far away from bugs with the god animal protecting
 */
@Controller
@RequestMapping("/pay")
public class PayController {

	public static final String SUCCESS = "SUCCESS";

	public static final String FAIL = "FAIL";

	public static final String OK = "OK";

	java.text.DecimalFormat df = new java.text.DecimalFormat("0");

	@Autowired
	private WxBillService wxBillService;

	@Autowired
	private WxInterfaceLogService wxInterfaceLogService;

	@Autowired
	private WxBillErrorService wxBillErrorService;

	@Autowired
	private WxPrePayLogService wxPrePayLogService;

	public PayController() {
		System.out.println("PayController构造函数");
	}

	/**
	 * 统一下单 应用场景：商户系统先调用该接口在微信支付服务后台生成预支付交易单，返回正确的预支付交易回话标识后再在APP里面调起支付。
	 * 
	 * @param WxPrePayDomain
	 *            前台获取的支付信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/weixinPrePay.do", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, String> weixinPrePay(WxPrePayDomain wxPrePayDomain, HttpServletRequest request) {
		//订单时间
		String dateString=DateUtil.dateToString(new Date(), "yyyyMMddHHmmss");
		// 商户单号 户号_wx时间戳
		String out_trade_no = wxPrePayDomain.getTrade_no() + "_wx"+ dateString;

		// 生成预支付传输xml
		String weixinPrePayFile = buildWeixinPrePayFile(wxPrePayDomain, request, out_trade_no);

		// 将xml发送到微信服务端 并取得返回值
		String result = PayCommonUtil.httpsRequest(Global.getConfig("master.weixinUrl"), "POST", weixinPrePayFile);

		System.out.println("======================预支付返回xml" + result);

		// 微信预支付返回结果xml转map
		Map<String, String> map = PayCommonUtil.doXMLParse(result);

		// 生成支付签名
		String paySign = buildWeixinPrePaySign(map);

		// 营销系统内部保存账单 并且构造前台返回集合
		Map<String, String> returnMap = buildWxBill(map, wxPrePayDomain, paySign, out_trade_no);

		// 返回给前台报文保存
		buildWxPrePayLog(map, wxPrePayDomain, out_trade_no);

		return returnMap;
	}

	/**
	 * 回调函数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws JDOMException
	 */
	@RequestMapping(value = "/notifyWeiXinPay.do", produces = MediaType.APPLICATION_JSON_VALUE)
	// @RequestDescription("支付回调地址")
	@ResponseBody
	public void notifyWeiXinPay(HttpServletRequest request, HttpServletResponse response) throws IOException {

		System.out.println(new Date() + "========================微信支付回调=========================");

		// 从request获取回调参数 转成map
		TreeMap<String, String> params = PayCommonUtil.GetMapFormRequest(request);

		// 校验
		boolean checkFlag = checkOrderStatus(params);

		Map<String, String> return_data = new HashMap<>();
		// 不成功返回 失败
		if (!checkFlag) {
			return_data.put("return_code", FAIL);
			return_data.put("return_msg", "return_code不正确");
		}

		// 成功返回消息
		return_data.put("return_code", SUCCESS);
		return_data.put("return_msg", OK);

		String checkMsg = PayCommonUtil.GetMapToXML(return_data);
		response.getWriter().write(checkMsg);
		response.getWriter().flush();
		response.getWriter().close();

		// 营销入账
		dealWxBill(params);

	}

	/**
	 * <p>
	 * Title: checkOrderStatus
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 判断支付状态
	 */
	private boolean checkOrderStatus(TreeMap<String, String> params) {
		System.out.println(new Date() + "=========================微信返回成功========================");

		// 取入库金额
		String returnMoney = wxBillService.checkMoney(params.get("out_trade_no").split("_")[0],
				params.get("out_trade_no").split("_")[1]);

		/*if ((SUCCESS).equals(params.get("return_code")) && (SUCCESS).equals(params.get("return_code"))) {

			// 校验签名 以及 返回金额与预支付入库金额
			if (PayCommonUtil.isTenpaySign(params) && (params.get("cash_fee")).equals(returnMoney)) {

				System.out.println(new Date() + "=======================付款成功=========================");
				// 进行营销入账
				return true;
			}
		}*/

		// 若失败 调用微信方法 查询入账结果
		String weixinPayQuery = findWeiXinPayResult(params);

		System.out.println("=======================入账查询结果=========================" + weixinPayQuery);

		// 微信预支付返回结果xml转map
		TreeMap<String, String> weixinPayQueryParams = PayCommonUtil.doXMLParse(weixinPayQuery);

		// 微信返回入账成功
		if ((SUCCESS).equals(weixinPayQueryParams.get("return_code"))
				&& (SUCCESS).equals(weixinPayQueryParams.get("trade_state"))
				&& (OK).equals(weixinPayQueryParams.get("return_msg"))) {
			if ((weixinPayQueryParams.get("cash_fee")).equals(returnMoney)) {
				return true;
			}
		}

		return false;

	}

	/**
	 * 
	 * <p>
	 * Title: doTransFile
	 * </p>
	 * 
	 * <p>
	 * Description:生成预支付传输xml
	 * </p>
	 * 
	 */
	private String buildWeixinPrePayFile(WxPrePayDomain wxPrePayDomain, HttpServletRequest request,
			String out_trade_no) {
		// TODO Auto-generated method stub
		String randomString = PayCommonUtil.getRandomString(32);

		SortedMap<String, String> parameterMap = new TreeMap<String, String>();
		// 应用appid
		parameterMap.put("appid", Global.getConfig("master.appid"));
		// 商户号
		parameterMap.put("mch_id", Global.getConfig("master.mch_id"));
		parameterMap.put("device_info", "WEB");
		parameterMap.put("nonce_str", randomString);
		parameterMap.put("body", wxPrePayDomain.getDescription());
		// 附加参数
		parameterMap.put("attach", wxPrePayDomain.getMon());
		// 编码规则 户号_wx+时间戳
		parameterMap.put("out_trade_no", out_trade_no);
		parameterMap.put("fee_type", "CNY");

		// 接口中参数支付金额单位为【分】，参数值不能带小数，所以乘以100
		BigDecimal total = (wxPrePayDomain.getTotalAmount()).multiply(new BigDecimal(100));
		parameterMap.put("total_fee", df.format(total));
		parameterMap.put("spbill_create_ip", PayCommonUtil.getRemoteHost(request));
		parameterMap.put("notify_url", Global.getConfig("master.wxnotify"));

		// 公众号为"JSAPI"
		parameterMap.put("trade_type", "JSAPI");
		// trade_type为JSAPI是 openid为必填项
		parameterMap.put("openid", wxPrePayDomain.getOpenId());

		// 生成预支付 签名
		String sign = PayCommonUtil.createSign("UTF-8", parameterMap);
		parameterMap.put("sign", sign);

		// 将组装的map转成xml
		String requestXML = PayCommonUtil.getRequestXml(parameterMap);
		// System.out.println("================发送微信服务器的xml" + requestXML);
		return requestXML;
	}

	/**
	 * 
	 * <p>
	 * Title: buildWeixinPrePaySign
	 * </p>
	 * 
	 * <p>
	 * Description:生成支付签名
	 * </p>
	 * 
	 */
	private String buildWeixinPrePaySign(Map<String, String> map) {
		// TODO Auto-generated method stub
		Long time = (System.currentTimeMillis() / 1000);
		SortedMap<String, String> paySignMap = new TreeMap<String, String>();
		paySignMap.put("appId", map.get("appid"));
		paySignMap.put("timeStamp", time.toString());
		paySignMap.put("nonceStr", map.get("nonce_str"));
		paySignMap.put("package", "prepay_id=" + map.get("prepay_id"));
		paySignMap.put("signType", Global.getConfig("master.encryptType"));
		return PayCommonUtil.createSign("UTF-8", paySignMap);
		// System.out.println("=======================paySign" + paySign);
	}

	/**
	 * 
	 * <p>
	 * Title: buildWxBill
	 * </p>
	 * 
	 * <p>
	 * Description:营销系统内部保存账单 并且构造前台返回集合
	 * </p>
	 * 
	 */
	private Map<String, String> buildWxBill(Map<String, String> map, WxPrePayDomain wxPrePayDomain, String paySign,
			String out_trade_no) {
		Map<String, String> returnMap = new HashMap<String, String>();
		Long time = (System.currentTimeMillis() / 1000);
		BigDecimal total = (wxPrePayDomain.getTotalAmount()).multiply(new BigDecimal(100));
		System.out.println("=========================total=================================" + total);
		if ((SUCCESS).equals(map.get("return_code")) && (OK).equals(map.get("return_msg"))) {
			// 成功后构造返回值 给前台
			returnMap.put("package", map.get("prepay_id"));
			returnMap.put("nonceStr", map.get("nonce_str"));
			returnMap.put("signType", Global.getConfig("master.encryptType"));
			returnMap.put("appId", map.get("appid"));
			returnMap.put("paySign", paySign);
			returnMap.put("timestamp", time.toString());
			returnMap.put("signature", Sign.sign(wxPrePayDomain.getTokenUrl(), map.get("nonce_str"), time.toString()));
			returnMap.put("returnCode", map.get("return_code"));
			returnMap.put("returnMsg", map.get("return_msg"));

			// 保存账单
			System.out.println(new Date() + "================开始保存账单=========================");
			try {
				WxBillDomain wxBillDomain = new WxBillDomain();
				wxBillDomain.setUserNo(out_trade_no.split("_")[0]);
				wxBillDomain.setMoney(df.format(total));
				wxBillDomain.setTradeNo(out_trade_no.split("_")[1]);
				wxBillDomain.setZfFlag(Character.valueOf('0'));
				wxBillDomain.setJzFlag(Character.valueOf('0'));
				wxBillDomain.setMon(wxPrePayDomain.getMon());
				wxBillDomain.setPrePayDate(DateUtil.stringtoDate(out_trade_no.split("_wx")[1],"yyyyMMddHHmmss"));
				wxBillService.add(wxBillDomain);
				System.out.println(new Date() + "================账单保存成功=========================");
			} catch (Exception e) {
				e.printStackTrace();
				WxBillErrorDomain wxBillErrorDomain = new WxBillErrorDomain();
				wxBillErrorDomain.setUserNo(out_trade_no.split("_")[0]);
				wxBillErrorDomain.setMoney(df.format(total));
				wxBillErrorDomain.setTradeNo(out_trade_no.split("_")[1]);
				wxBillErrorDomain.setFlag(Character.valueOf('1'));
				wxBillErrorDomain.setDate(DateUtil.stringtoDate(out_trade_no.split("_wx")[1],"yyyyMMddHHmmss"));
				// 异常信息
				wxBillErrorDomain.setError(e.getCause().toString());
				wxBillErrorDomain.setMon(wxPrePayDomain.getMon());
				wxBillErrorService.add(wxBillErrorDomain);

			}

		} else {
			System.out.println(new Date() + "================微信预支付失败=========================");
			returnMap.put("returnCode", map.get("return_code"));
			returnMap.put("returnMsg", map.get("return_msg"));
		}
		return returnMap;
	}

	/**
	 * 
	 * <p>
	 * Title: buildWxPrePayLog
	 * </p>
	 * 
	 * <p>
	 * Description:返回给前台报文保存
	 * </p>
	 * 
	 * @param map
	 * 
	 */
	private void buildWxPrePayLog(Map<String, String> map, WxPrePayDomain wxPrePayDomain, String out_trade_no) {
		Long time = (System.currentTimeMillis() / 1000);
		WxPrePayLogDomain wxPrePayLogDomain = new WxPrePayLogDomain();
		wxPrePayLogDomain.setAppId(map.get("appid"));
		wxPrePayLogDomain.setNonceStr(map.get("nonce_str"));
		wxPrePayLogDomain.setPrepayId(map.get("prepay_id"));
		wxPrePayLogDomain.setReturnCode(map.get("return_code"));
		wxPrePayLogDomain.setReturnMsg(map.get("return_msg"));
		wxPrePayLogDomain.setSignature(Sign.sign(wxPrePayDomain.getTokenUrl(), map.get("nonce_str"), time.toString()));
		wxPrePayLogDomain.setSignType(Global.getConfig("master.encryptType"));
		wxPrePayLogDomain.setTimestamp(time.toString());
		wxPrePayLogDomain.setUserNo(out_trade_no.split("_")[0]);
		wxPrePayLogDomain.setTradeNo(out_trade_no.split("_")[1]);
		wxPrePayLogDomain.setZfDate(DateUtil.stringtoDate(out_trade_no.split("_wx")[1],"yyyyMMddHHmmss"));
		wxPrePayLogService.add(wxPrePayLogDomain);

	}

	/**
	 * 
	 * <p>
	 * Title: dealWxBill
	 * </p>
	 * 
	 * <p>
	 * Description:处理营销入账
	 * </p>
	 * 
	 * 
	 */
	private synchronized boolean dealWxBill(TreeMap<String, String> params) {

		// 返回值是分 转成元
		String total_fee = params.get("total_fee");
		double money = Double.valueOf(total_fee) / 100;
		String attach = params.get("attach");

		// 更改支付时间和支付标识
		WxBillDomain wxBillDomain = new WxBillDomain();
		wxBillDomain.setUserNo(params.get("out_trade_no").split("_")[0]);
		wxBillDomain.setTradeNo(params.get("out_trade_no").split("_")[1]);
		wxBillDomain.setZfFlag(Character.valueOf('1'));
		wxBillDomain.setZfDate(DateUtil.stringtoDate(params.get("time_end"),"yyyyMMddHHmmss"));
		// 订单号
		wxBillDomain.setTransactionId(params.get("transaction_id"));
		
		wxBillService.update(wxBillDomain);

		String jz_flag=wxBillService.findByTransactionId(params.get("out_trade_no").split("_")[0],
				params.get("out_trade_no").split("_")[1]);
		
		if("1".equals(jz_flag)) {
			return false;
		}
		
		System.out.println(new Date() + "=====================调用结账方法=====================");

		String url = Global.getConfig("master.interFaceUrl");
		String param = "?userNo=" + params.get("out_trade_no").split("_")[0] + "&mon=" + attach + "&money=" + money+"&transactionId="+params.get("transaction_id");
		String returnFlag = JsapiTicketUtil.httpPost(url + param);

		// 保存与营销系统的报文日志
		WxInterfaceLogDomain wxInterfaceLogDomain = new WxInterfaceLogDomain();
		wxInterfaceLogDomain.setUrl(url);
		wxInterfaceLogDomain.setParam(param);
		wxInterfaceLogDomain.setReturnFlag(returnFlag);
		wxInterfaceLogDomain.setJzDate(new Date());
		wxInterfaceLogDomain.setTransactionId(params.get("transaction_id"));
		wxInterfaceLogService.add(wxInterfaceLogDomain);

		if (("true").equals(returnFlag)) {
			// 成功更改结账时间和结账标识
			WxBillDomain wxBillDomainJZ = new WxBillDomain();
			wxBillDomainJZ.setUserNo(params.get("out_trade_no").split("_")[0]);
			wxBillDomainJZ.setTradeNo(params.get("out_trade_no").split("_")[1]);
			wxBillDomainJZ.setJzFlag(Character.valueOf('1'));
			wxBillDomainJZ.setJzDate(DateUtil.stringtoDate(params.get("time_end"),"yyyyMMddHHmmss"));
			wxBillService.update(wxBillDomainJZ);
			System.out.println(new Date() + "=======================入账完成=======================");
		} else {
			// 失败更改结账时间
			WxBillDomain wxBillDomainJZ = new WxBillDomain();
			wxBillDomainJZ.setUserNo(params.get("out_trade_no").split("_")[0]);
			wxBillDomainJZ.setTradeNo(params.get("out_trade_no").split("_")[1]);
			wxBillDomainJZ.setJzDate(DateUtil.stringtoDate(params.get("time_end"),"yyyyMMddHHmmss"));
			wxBillService.update(wxBillDomainJZ);
			System.out.println(new Date() + "=======================入账失败=======================");
		}
		
		return true;

	}

	/**
	 * 
	 * <p>
	 * Title: findWeiXinPayResult
	 * </p>
	 * 
	 * <p>
	 * Description://查询入账结果
	 * </p>
	 * 
	 * @param request
	 * 
	 */
	private String findWeiXinPayResult(TreeMap<String, String> params) {
		String randomString = PayCommonUtil.getRandomString(32);
		// TODO Auto-generated method stub
		SortedMap<String, String> parameterMap = new TreeMap<String, String>();
		// 应用appid
		parameterMap.put("appid", Global.getConfig("master.appid"));
		// 商户号
		parameterMap.put("mch_id", Global.getConfig("master.mch_id"));
		parameterMap.put("transaction_id", params.get("transaction_id"));
		parameterMap.put("nonce_str", randomString);

		parameterMap.put("sign_type", Global.getConfig("encryptType"));
		// 生成预支付 签名
		String sign = PayCommonUtil.createSign("UTF-8", parameterMap);
		parameterMap.put("sign", sign);
		// 将组装的map转成xml
		String weixinPayQuery = PayCommonUtil.getRequestXml(parameterMap);
		// System.out.println("================发送微信服务器的xml" + requestXML);
		// 将xml发送到微信服务端 并取得返回值
		String result = PayCommonUtil.httpsRequest(Global.getConfig("master.weixinQueryUrl"), "POST", weixinPayQuery);
		return result;
	}

}
