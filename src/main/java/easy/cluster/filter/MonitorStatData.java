package easy.cluster.filter;

/**
 * 监控发送数据
 * @author lixiaojing3
 *
 */
public class MonitorStatData {
	/**
	 * 服务接口名称
	 */
	public String serviceName;
	/**
	 * 服务接口方法名称
	 */
	public String methodName;
	/**
	 * 服务IP
	 */
	public String ip;
	/**
	 * 服务端口
	 */
	public String port;
	/**
	 * 统计时间
	 */
	public int stattime;
	/**
	 * 每分钟响应数
	 */
	public int responseFrequency = 0;
	/**
	 * 每分钟请求数
	 */
	public int requestFrequency = 0;
	/**
	 * 每分钟请求异常次数
	 */
	public int errorFrequency = 0;
	/**
	 * 一分钟内最大响应时间
	 */
	public int maxResponseTime = 0;
	/**
	 * 一分钟内最小响应时间
	 */
	public int minResponseTime = 0;
	/**
	 * 平均响应时间
	 */
	public double averageResponseTime = 0;
}
