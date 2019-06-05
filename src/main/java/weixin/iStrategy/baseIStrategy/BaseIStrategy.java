/**
 * 2019年5月23日
 * 
 */
package weixin.iStrategy.baseIStrategy;

/**
 * <p>
 * Title: BaseIStrategy.java
 * </p>
 * <p>
 * Description:
 * 微信模板--策略模式实现
 * 
 * 后期增加模板消息步骤
 * 1.写好实现类继承BaseIStrategy 
 * 2.前台发送参数结构 templateNo=模板号
 * 3.将模板号及处理类写到BaseEnum中，注意处理类首字母小写（spring注入首字母小写）
 * templateNo=1&interFaceClass=wxTemplateDeal
 * </p>
 * 
 * @author zjd
 * @param <T>
 * @date 2019年5月23日
 * @remark Code is far away from bugs with the god animal protecting
 */
public interface BaseIStrategy<T> {
	//处理发送消息和返回消息
	public void dealMsg(String accessToken,T t);
	//发送方法
	public int sendMsg(String templateNo,String accessToken);
	

}
