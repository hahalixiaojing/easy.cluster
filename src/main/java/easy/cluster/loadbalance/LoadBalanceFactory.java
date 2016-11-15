package easy.cluster.loadbalance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import easy.cluster.ILoadBalance;

public abstract class LoadBalanceFactory {
	private static final Map<String, ILoadBalance> loadbalance = new HashMap<>();

	static {
		loadbalance.put(RandomLoadBalance.NAME, new RandomLoadBalance());
		loadbalance.put(ConsistentHashLoadBalance.NAME,
				new ConsistentHashLoadBalance(new DefaultConsistenHashNode()));
	}

	public static void register(ILoadBalance balance) {
		loadbalance.put(balance.getName(), balance);
	}

	public static List<ILoadBalance> loadbalances() {
		return new ArrayList<ILoadBalance>(loadbalance.values());
	}

	public static ILoadBalance getloadBalance(String name) {
		return loadbalance.get(name);
	}
}
