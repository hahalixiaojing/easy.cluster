package easy.cluster.filter;

import java.util.Calendar;

import easy.cluster.IFilter;
import easy.cluster.IFilterChain;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

public class MonitorFilter implements IFilter {

	private static final MonitorWorker worker = new MonitorWorker();

	@Override
	public <T> T doFilter(Node node, Invocation invocation, Class<T> cls, IFilterChain filterChain) throws Exception {

		MonitorDataKey key = new MonitorDataKey(invocation.getServiceName(), invocation.getMethodName(),
				node.getAddress(), node.getPort(), this.getStatTime(),invocation.getMonitorDataWriter());

		MonitorData data = worker.getMonitorData(key);
		try {
			data.incrementRequestFrequency();
			long start = System.currentTimeMillis();
			T result = filterChain.doFilter(node, invocation, cls);
			long end = System.currentTimeMillis();

			int responseTime = (int)(end - start);

			data.incrementResponseFrequency();
			data.updateMaxResponseTime(responseTime);
			data.updateMinResponseTime(responseTime);
			data.updateTotalResponseTime(responseTime);

			return result;
		} catch (Exception e) {
			data.incrementErrorFrequency();
			throw e;
		}
	}

	private int getStatTime() {
		Calendar c = Calendar.getInstance();

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);

		String stattime = String.format("%s%s%s%s%s", year, month, day, hour, minute);
		return Integer.parseInt(stattime);
	}

}
