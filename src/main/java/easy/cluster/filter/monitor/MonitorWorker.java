package easy.cluster.filter.monitor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

public class MonitorWorker implements Runnable {
	private static final ConcurrentHashMap<MonitorDataKey, MonitorData> m = new ConcurrentHashMap<>();
	private Thread t;

	public MonitorWorker() {

		t = new Thread(this);
		t.setDaemon(true);
		t.start();
	}

	public MonitorData getMonitorData(MonitorDataKey key) {
		return m.putIfAbsent(key, new MonitorData());
	}

	@Override
	public void run() {
		final int max = 9;
		while (true) {
			MonitorDataKey[] keys = this.getKeys();
			List<MonitorStatData> sendData = new ArrayList<MonitorStatData>();
			for (int i = 0; i < keys.length; i++) {

				if (!keys[i].isEnd()) {
					continue;
				}
				if (i >= max) {
					break;
				}
				MonitorData data = m.remove(keys[i]);
				sendData.add(this.convert(keys[i], data));
			}
			
			
		}

	}

	private MonitorStatData convert(MonitorDataKey key, MonitorData data) {
		MonitorStatData senddata = new MonitorStatData();
		
		senddata.averageResponseTime = data.getAverageResponseTime();
		senddata.errorFrequency = data.getErrorFrequency();
		senddata.ip = key.getIp();
		senddata.maxResponseTime = data.getMaxResponseTime();
		senddata.methodName = key.getMethodName();
		senddata.minResponseTime = data.getMinResponseTime();
		senddata.port = key.getPort();
		senddata.requestFrequency = data.getRequestFrequency();
		senddata.responseFrequency = data.getResponseFrequency();
		senddata.serviceName = key.getServiceName();
		senddata.stattime = key.getStattime();
		return senddata;
	}

	private MonitorDataKey[] getKeys() {
		TreeSet<MonitorDataKey> treeSet = new TreeSet<MonitorDataKey>(new Comparator<MonitorDataKey>() {
			@Override
			public int compare(MonitorDataKey o1, MonitorDataKey o2) {
				return o1.getStattime() - o2.getStattime();
			}
		});

		treeSet.addAll(m.keySet());

		MonitorDataKey[] keys = treeSet.toArray(new MonitorDataKey[0]);
		return keys;
	}
}
