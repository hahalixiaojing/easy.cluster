package easy.cluster.support;


import easy.cluster.ICluster;
import easy.cluster.IDirectory;
import easy.cluster.IInvoker;
import easy.cluster.ILoadBalance;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

/**
 * 快速失败
 * 
 * @author 晓静
 *
 */
public class FailfastCluster implements ICluster {

	public static final String NAME = "FailfastCluster";

	@Override
	public String getName() {
		return NAME;
	};

	@Override
	public <T> T invoke(IDirectory directory, Object[] paramter,
			ILoadBalance loadbanlance, IInvoker invoker, Class<T> cls,
			Invocation invocation) throws Exception {

		Node node = loadbanlance.select(directory.getNodes(), invocation);

		return invoker.doInvoke(node, invocation, cls);
	}
}
