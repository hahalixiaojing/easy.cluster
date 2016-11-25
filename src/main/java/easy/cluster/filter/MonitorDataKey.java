package easy.cluster.filter;

import java.util.Calendar;

public class MonitorDataKey {

	/**
	 * 服务接口名称
	 */
	private String serviceName;
	/**
	 * 服务接口方法名称
	 */
	private String methodName;
	/**
	 * 服务IP
	 */
	private String ip;
	/**
	 * 服务端口
	 */
	private String port;
	/**
	 * 统计时间
	 */
	private int stattime;
	/**
	 * 监控数据writer名称
	 */
	private String monitorWriterName;

	public MonitorDataKey(String serviceName, String methodName, String ip, String port, int stattime,String monitorWriterName) {
		this.setServiceName(serviceName);
		this.setMethodName(methodName);
		this.setIp(ip);
		this.setPort(port);
		this.setStattime(stattime);
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public int getStattime() {
		return stattime;
	}

	public void setStattime(int stattime) {
		this.stattime = stattime;
	}
	public String getMonitorWriterName() {
		return monitorWriterName;
	}
	/**
	 * 监控数据是否收集完毕
	 * @return
	 */
	public boolean isEnd() {
		int current = this.currentTiime();
		return current - this.getStattime() >= 2;
	}

	private int currentTiime() {
		Calendar c = Calendar.getInstance();

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		String stattime = String.format("%s%s%s%s%s", year, month, day, hour, minute);
		return Integer.parseInt(stattime);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MonitorDataKey)) {
			return false;
		}

		MonitorDataKey m = (MonitorDataKey) obj;

		if (!m.getServiceName().equals(this.getServiceName())) {
			return false;
		}
		if (!m.getMethodName().equals(this.getMethodName())) {
			return false;
		}
		if (!m.getIp().equals(this.getIp())) {
			return false;
		}
		if (m.getStattime() != this.getStattime()) {
			return false;
		}
		if (!(m.getPort().equals(this.getPort()))) {
			return false;
		}
		return true;

	}

	@Override
	public int hashCode() {

		String str = this.getServiceName() + this.getMethodName() + this.getIp() + this.getPort() + this.getStattime();

		return str.hashCode();
	}

	
}
