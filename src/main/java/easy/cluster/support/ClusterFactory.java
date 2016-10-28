package easy.cluster.support;

import java.util.HashMap;

import easy.cluster.ICluster;

public abstract class ClusterFactory {
	private static final HashMap<String, ICluster> clusters = new HashMap<>();

	static {
		clusters.put(FailfastCluster.NAME, new FailfastCluster());
		clusters.put(FailoverCluster.NAME, new FailoverCluster());
		clusters.put(ForkingCluster.NAME, new ForkingCluster());
		clusters.put(FailsafeCluster.NAME, new FailsafeCluster());
	}

	public static ICluster getCluser(String name) {
		return clusters.get(name);
	}

	public static void register(ICluster cluster) {
		clusters.put(cluster.getName(), cluster);
	}
}
