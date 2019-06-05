/**
 * 2019年5月23日
 * 
 */
package weixin.iStrategy.context;

import weixin.iStrategy.baseIStrategy.BaseIStrategy;

/**  
* <p>Title: StrategyContext.java</p>  
* <p>Description: 
* 策略模式控制器
* </p>  
* @author zjd  
* @date 2019年5月23日  
* @remark Code is far away from bugs with the god animal protecting
*/
@SuppressWarnings("rawtypes")
public class StrategyContext {
	private BaseIStrategy baseIStrategy;

	/**
	 * @param baseIStrategy
	 */
	public StrategyContext(BaseIStrategy baseIStrategy) {
		super();
		this.baseIStrategy = baseIStrategy;
	}
	
	@SuppressWarnings("unchecked")
	public void dealMsg(String accessToken,Object t) {
		baseIStrategy.dealMsg(accessToken,t);
	}
	
	public int sendMsg(String templateNo,String accessToken) {
		return baseIStrategy.sendMsg(templateNo,accessToken);
	}
	
}
