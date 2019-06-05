/**
 * 2018年11月21日
 * 
 */
package weixin;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**  
* <p>Title: XmlParseString.java</p>  
* <p>Description: </p>  
* @author zjd  
* @date 2018年11月21日  
* @remark Code is far away from bugs with the god animal protecting
*/
public class XmlParseString {
	
	// xml解析
	public static Map<String, String> doXMLParse(String strxml) {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");

		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String, String> m = new HashMap<String, String>();
		try {
			InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
			SAXBuilder builder = new SAXBuilder();
			Document doc;
			doc = builder.build(in);
			Element root = doc.getRootElement();
			
			List<Element> list = root.getChildren();
			
			Iterator<Element> it = list.iterator();
			
			while(it.hasNext()) {
				
			}
			
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String k = e.getName();
				String v = "";
				List<Element> children = e.getChildren();
				if (children.isEmpty()) {
					v = e.getTextNormalize();
				} else {
					v = getChildrenText(children);
				}

				m.put(k, v);
			}
			// 关闭流
			in.close();
		} catch (JDOMException e1) {
			System.out.println(new Date() + "=========================xml转map错误==================");
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			System.out.println(new Date() + "=========================输入参数解析错误==================");
			e1.printStackTrace();
		}

		return m;
	}
	public static String getChildrenText(List<Element> children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator<Element> it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List<Element> list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}

		return sb.toString();
	}
	public static String readXml(String fileName) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
		String line = "";
		StringBuffer xml = new StringBuffer();
		while ((line = br.readLine()) != null)
		{
			xml.append(line).append('\n');
		} 
		return xml.toString();
	}
	
	public static void main(String[] args) throws IOException {
		String xml=readXml("C:\\Users\\Administrator\\Desktop\\fpkj.xml");
		System.out.println(xml);
		
		Map<String, String>xmlMap=doXMLParse(xml);
		System.out.println(xmlMap.get("FPQQLSH"));
	}
}
