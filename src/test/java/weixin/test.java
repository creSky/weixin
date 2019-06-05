/**
 * 2018年9月28日
 * 
 */
package weixin;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Predicate;

import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import weixin.domain.TemplateData;
import weixin.domain.TemplateMessage;
import weixin.domain.WxBillDomain;
import weixin.domain.WxBillErrorDomain;
import weixin.enumeration.BaseEnum;
import weixin.iStrategy.impl.WxTemplateDeal;
import weixin.service.WxBillErrorService;
import weixin.service.WxBillService;
import weixin.util.DateUtil;
import weixin.util.JsapiTicketUtil;
import weixin.util.PayCommonUtil;
import weixin.util.Sign;

/**
 * @author Administrator
 *
 */
public class test {
	public static void main(String[] args) throws InterruptedException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		// 测试web通信
		// String url="http://172.21.29.34/wisdomServer/SaveMoneyServlet";
		// String param="?userNo=19&mon=201809&money=0.01";
		// String returnFlag=JsapiTicketUtil.httpPost(url+param);
		// if(("true").equals(returnFlag)) {
		// System.out.println("success");
		// }else {
		// System.out.println("fail");
		//
		// }
		// Long time = (System.currentTimeMillis() / 1000);
		// System.out.println(time);
		// System.out.println(System.currentTimeMillis() / 1000);
		// Thread.sleep(1000);
		// System.out.println(time);
		// System.out.println(System.currentTimeMillis() / 1000);
		//

		// String randomString = PayCommonUtil.getRandomString(32);
		// System.out.println(randomString);
		// System.out.println(randomString);
		// Map<Object, Object> aa=new HashMap<>();
		// aa.put(null, null);
		//
		// System.out.println("aa"+aa.get(null));
		//
		// System.out.println(Integer.MAX_VALUE);
		// System.out.println(Integer.MIN_VALUE);
		// 测试字符串提取json数据
		// System.out.println("json字符串转java代码");
		// String getAsString = "{\"total\":2,\r\n" +
		// "\"count\":2,\r\n" +
		// "\"data\":{\r\n" +
		// "\"openid\":[\"OPENID1\",\"OPENID2\"]},\r\n" +
		// "\"next_openid\":\"NEXT_OPENID\"\r\n" +
		// "}";

		// JSONObject jsonObject = JSONObject.parseObject(getAsString);
		//
		// System.out.println(jsonObject.get("total"));
		//
		// System.out.println(jsonObject.get("data"));
		//
		// JSONArray aa= jsonObject.getJSONObject("data").getJSONArray("openid");
		//
		// for (Object object : aa) {
		// System.out.println(object.toString());
		// }
		// 测试json转换
		// WeixinUserList jsonObject = JSONObject.parseObject(getAsString,
		// WeixinUserList.class);
		//
		// List<String> aa=jsonObject.getData().getOpenid();
		//
		// for (String string : aa) {
		// System.out.println(string);
		// }
		//
		// System.out.println("aaaa");

		// 测试文件流
		// File file = new File("D:/test.txt");
		// if(!file.getParentFile().exists()){
		// file.getParentFile().mkdirs();
		// }
		//
		// //2：准备输出流
		// Writer out = new FileWriter(file);
		// out.write("CONS_ID==="+userNo+"MON==="+mon+"FACT_MONEY==="+factMoney);
		// out.close();

		// 测试线程池
		// ExecutorService executorService =
		// Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		// System.out.println(new Date());
		// for (int i = 0; i < 30; i++) {
		//
		// final int index = i;
		// executorService.execute(new Runnable() {
		// public void run() {
		// show(index);
		// }
		// });
		//
		// }
		// //释放线程池
		// executorService.shutdown();
		// System.out.println("斑斑驳驳bbb");

		// System.out.println(TemplateEnum.getEnumByKey("1").getValue());
		// List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7);
		// list.forEach(n -> System.out.println(n));
		// list.forEach(System.out::println);
		System.out.println(DateUtil.stringtoDate("20091225091010","yyyyMMddHHmmss"));
	/*	Predicate<Student> maleStudent = s -> s.age >= 20 && "male".equals(s.gender);
		
		Predicate<Student> femaleStudent = s -> s.age > 15 && "female".equals(s.gender);

		Function<Student, String> maleStyle = s -> "Hi, You are male and age " + s.age;

		Function<Student, String> femaleStyle = s -> "Hi, You are female and age " + s.age;

		test test1 = new test();
		
		test.Student s1 = test1.new Student(21, "male");
		
		if (maleStudent.test(s1)) {
			System.out.println(s1.customShow(maleStyle));
		} else if (femaleStudent.test(s1)) {
			System.out.println(s1.customShow(femaleStyle));
		}
	}

	// 测试线程池
	public static void show(int i) throws Exception {
		// 构造一个字节缓冲输入流对象
		FilterInputStream bis = new BufferedInputStream(new FileInputStream("bos.txt"));
		// 一次读取一个字节数组
		byte[] bys = new byte[1024];
		int len = 0;
		while ((len = bis.read(bys)) != -1) {
			System.out.println(new String(bys, 0, len)); // 通过使用平台的默认字符集解码指定的 byte 子数组，构造一个新的 String。
		}
		// 释放资源
	}

	class Student {
		public int age;
		public String gender;

		public Student(int age, String gender) {
			this.age = age;
			this.gender = gender;
		}

		public String customShow(Function<Student, String> fun) {
			return fun.apply(this);
		}*/
		WxTemplateDeal wxTemplateDeal= (WxTemplateDeal) Class.forName("weixin.iStrategy.impl.WxTemplateDeal").newInstance();
		System.out.println("1111");
	}

}
