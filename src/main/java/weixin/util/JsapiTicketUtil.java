package weixin.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;

import weixin.config.Global;

public class JsapiTicketUtil {

	public static String getJsapiTicket() {
		String access_token = getAccessToken();
		
		String jsapiTicketUrl = Global.getConfig("master.jsapiTicketUrl").replace("ACCESS_TOKEN",access_token);
		
		String result = httpGet(jsapiTicketUrl);
		
		String jsapi_ticket = JSONObject.parseObject(result).getString("ticket");
		
		// System.out.println(result);
		System.out.println("====================jsapi_ticket" + jsapi_ticket);
		return jsapi_ticket;
	}

	public static String getAccessToken() {
		// TODO Auto-generated method stub
		// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET

		String getTokenUrl = Global.getConfig("master.tokenUrl").replace("APPID", Global.getConfig("master.appid"))
				.replace("APPSECRET", Global.getConfig("master.api_appsecret"));

		System.out.println("组装 getTokenUrl为===============" + getTokenUrl);

		String result = httpGet(getTokenUrl);

		String error = JSONObject.parseObject(result).getString("errcode");

		if (error != null) {
			System.out.println("返回的errcode为==================" + JSONObject.parseObject(result).getString("errcode"));
			System.out.println("返回的errmsg为==================" + JSONObject.parseObject(result).getString("errmsg"));
		}

		System.out.println(
				"返回的access_token为==================" + JSONObject.parseObject(result).getString("access_token"));

		return JSONObject.parseObject(result).getString("access_token");

	}

	/**
	 * post请求
	 * 
	 * @param url
	 * @return
	 */

	public static String httpPost(String url) {
		// post请求返回结果
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		HttpPost method = new HttpPost(url);
		
//		 3. 设置POST请求传递参数 不完成post方法
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//        params.add(new BasicNameValuePair("username", "test"));
//        params.add(new BasicNameValuePair("password", "12356"));
//        try {
//            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
//            method.setEntity(entity);
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
		String str = "";
		try {
			HttpResponse result = httpClientBuilder.build().execute(method);
			url = URLDecoder.decode(url, "UTF-8");
			/** 请求发送成功，并得到响应 **/
			if (result.getStatusLine().getStatusCode() == 200) {
				try {
					/** 读取服务器返回过来的json字符串数据 **/
					str = EntityUtils.toString(result.getEntity());
				} catch (Exception e) {
					System.out.println("post请求提交失败:" + url);
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			System.out.println("post请求提交失败:" + url);
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * get 请求、
	 * 
	 * @param url
	 * @return
	 */
	public static String httpGet(String url) {
		// get请求返回结果
		String strResult = null;
		try {
			HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
			// 发送get请求
			HttpGet request = new HttpGet(url);
			HttpResponse response = httpClientBuilder.build().execute(request);

			/** 请求发送成功，并得到响应 **/
			if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
				/** 读取服务器返回过来的json字符串数据 **/
				strResult = EntityUtils.toString(response.getEntity());
			} else {
				System.out.println("get请求提交失败:" + url);

			}
		} catch (IOException e) {
			System.out.println("get请求提交失败:" + url);
			e.printStackTrace();
		}
		return strResult;
	}
}
