/**
 * 2018年9月26日
 * 
 */
package weixin.dao;

import weixin.domain.WxBillDomain;

/**
 * @author Administrator
 *
 */
public interface WxBillDAO {
	public int insert(WxBillDomain t);
	public int update(WxBillDomain t);
	public String findByKey(WxBillDomain t);
	public String findByTransactionId(WxBillDomain t);

}
