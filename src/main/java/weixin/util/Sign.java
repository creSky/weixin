package weixin.util;



import java.security.MessageDigest;
import java.util.Formatter;
import java.util.UUID;

public class Sign {
    /**
     * jsSdk 获取微信config
     *
     * @param url
     * @return
     */
	public static String sign(String url,String nonce_str,String timestamp) {
        String jsapi_ticket = JsapiTicketUtil.getJsapiTicket();
        System.out.println("=====================jsapi_ticket"+jsapi_ticket);
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        String string1 = "jsapi_ticket=" + jsapi_ticket +
                "&noncestr=" + nonce_str +
                "&timestamp=" + timestamp +
                "&url=" + url;
        try {
            signature = getSha1(string1);
            System.out.println("======================signature"+signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signature;
    }

    /**
     * 生成随机数
     *
     * @return
     */
    private static String create_nonce_str() {
        return UUID.randomUUID().toString().substring(0, 20);
    }

    /**
     * 当前日期字符串生成
     *
     * @return
     */
    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }

    /**
     *
     * @param hash
     * @return
     */
    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    public static String getSha1(String str) {

        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
