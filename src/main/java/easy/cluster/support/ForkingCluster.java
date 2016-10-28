package easy.cluster.support;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import easy.cluster.ICluster;
import easy.cluster.IDirectory;
import easy.cluster.IInvoker;
import easy.cluster.ILoadBalance;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

/**
 * 并行调用
 * 
 * @author 晓静
 *
 */
public class ForkingCluster implements ICluster {

	public static final String NAME = "ForkingCluster";
	private final ExecutorService executor = Executors.newCachedThreadPool();
	private final int forks = 3;
	private final int timeout = 10000;

	@Override
	public String getName() {

		return NAME;
	}

	@Override
	public <T> T invoke(IDirectory directory, Object[] paramter,
			ILoadBalance loadbanlance, IInvoker invoker, Class<T> cls,
			Invocation invocation) throws Exception {

		List<Node> selected;
		List<Node> nodes = directory.getNodes();
		if (this.forks <= 0 || this.forks >= nodes.size()) {
			selected = nodes;
		} else {
			selected = new ArrayList<>();

			for (int i = 0; i < this.forks; i++) {
				Node node = loadbanlance.select(nodes, invocation);

				if (!selected.contains(node)) {
					selected.add(node);
				}
			}
		}

		ArrayList<Callable<T>> tasks = new ArrayList<Callable<T>>();

		for (Node node : selected) {
			Callable<T> s = new Callable<T>() {
				@Override
				public T call() throws Exception {
					return invoker.doInvoke(new URL(node.getAddress()), invocation,
							cls);
				}
			};
			tasks.add(s);
		}

		return executor.invokeAny(tasks, this.timeout, TimeUnit.MICROSECONDS);
	}
}
