package easy.cluster.support;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import easy.cluster.ICluster;
import easy.cluster.IDirectory;
import easy.cluster.IInvoker;
import easy.cluster.ILoadBalance;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

/**
 * 失败重试
 * 
 * @author 晓静
 *
 */
public class FailoverCluster implements ICluster {

	public static final String NAME = "FailoverCluster";
	private static final int defaultRetries = 2;

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public <T> T invoke(IDirectory directory, Object[] paramter,
			ILoadBalance loadbanlance, IInvoker invoker, Class<T> cls,
			Invocation invocation) throws Exception {

		List<Node> invokedNodes = new ArrayList<>();

		int retries = -1;

		for (int i = 0; i <= defaultRetries; i++) {
			retries++;
			try {
				List<Node> subListNode = directory.getNodes().stream()
						.filter(n -> !invokedNodes.contains(n))
						.collect(Collectors.toList());

				Node node = loadbanlance.select(subListNode, invocation);

				invokedNodes.add(node);

				return invoker.doInvoke(new URL(node.getAddress()), invocation, cls);
			} catch (Exception e) {
				if (retries > defaultRetries) {
					throw e;
				}
			}
		}
		return null;
	}
}
