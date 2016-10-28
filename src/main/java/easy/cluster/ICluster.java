package easy.cluster;


import easy.cluster.invoker.Invocation;

public interface ICluster {
	String getName();

	<T> T invoke(IDirectory directory, Object[] parameters,
			ILoadBalance loadbanlance, IInvoker invoker,Class<T> cls, Invocation invocation)
			throws Exception;
}
