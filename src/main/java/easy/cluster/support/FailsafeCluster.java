package easy.cluster.support;

import easy.cluster.ICluster;
import easy.cluster.IDirectory;
import easy.cluster.IFilterChain;
import easy.cluster.IInvoker;
import easy.cluster.ILoadBalance;
import easy.cluster.Node;
import easy.cluster.filter.DefaultFilterChain;
import easy.cluster.invoker.Invocation;

/**
 * 失败安全
 * 
 * @author 晓静
 *
 */
public class FailsafeCluster implements ICluster {

	public static final String NAME = "FailsafeCluster";

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public <T> T invoke(IDirectory directory, Object[] paramter, ILoadBalance loadbanlance, IInvoker invoker,
			Class<T> cls, Invocation invocation) throws Exception {

		Node node = loadbanlance.select(directory.getNodes(), invocation);

		try {
			return new DefaultFilterChain(invocation.getFilters(), invoker).doFilter(node, invocation, cls);
		} catch (Exception e) {
			return null;
		}
	}

}
