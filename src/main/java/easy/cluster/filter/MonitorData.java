package easy.cluster.filter;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 监控数据
 * 
 * @author lixiaojing3
 *
 */
public class MonitorData {
	
	/**
	 * 每分钟响应数
	 */
	private AtomicInteger responseFrequency = new AtomicInteger(0);
	/**
	 * 每分钟请求数
	 */
	private AtomicInteger requestFrequency = new AtomicInteger(0);
	/**
	 * 每分钟请求异常次数
	 */
	private AtomicInteger errorFrequency = new AtomicInteger(0);
	/**
	 * 一分钟内最大响应时间
	 */
	private AtomicInteger maxResponseTime = new AtomicInteger(0);
	/**
	 * 一分钟内最小响应时间
	 */
	private AtomicInteger minResponseTime = new AtomicInteger(0);

	/**
	 * 一分钟总的响应时间
	 */
	private AtomicInteger totalResponseTime = new AtomicInteger(0);

	public int getResponseFrequency() {
		return responseFrequency.get();
	}

	public int getRequestFrequency() {
		return requestFrequency.get();
	}

	public int getErrorFrequency() {
		return errorFrequency.get();
	}

	public int getMaxResponseTime() {
		return this.maxResponseTime.get();
	}

	public int getMinResponseTime() {
		return minResponseTime.get();
	}

	public int getTotalResponseTime() {
		return totalResponseTime.get();
	}
	/**
	 * 平均响应时间
	 * @return
	 */
	public double getAverageResponseTime() {
		return this.getTotalResponseTime() / this.getResponseFrequency();
	}

	/**
	 * 增加响应计数
	 */
	public void incrementResponseFrequency() {
		this.responseFrequency.incrementAndGet();
	}

	/**
	 * 增加请求计数
	 */
	public void incrementRequestFrequency() {
		this.requestFrequency.incrementAndGet();
	}

	/**
	 * 增加错误计数
	 */
	public void incrementErrorFrequency() {
		this.errorFrequency.incrementAndGet();
	}

	/**
	 * 更新最大响应时间
	 * 
	 * @param responseTime
	 */
	public void updateMaxResponseTime(int responseTime) {

		while (true) {
			int maxResposneTime = this.maxResponseTime.get();
			if (this.maxResponseTime.compareAndSet(maxResposneTime, Math.max(maxResposneTime, responseTime))) {
				break;
			}
		}
	}
	/**
	 * 更新小响应时间
	 * @param responseTime
	 */
	public void updateMinResponseTime(int responseTime){
		while(true){
			int minResponsetime = this.minResponseTime.get();
			if(this.minResponseTime.compareAndSet(minResponsetime, Math.min(minResponsetime, responseTime))){
				break;
			}
		}
	}
	/**
	 * 累加总响应时间
	 * @param responseTime
	 */
	public void updateTotalResponseTime(int responseTime){
		this.totalResponseTime.addAndGet(responseTime);
	}
}
