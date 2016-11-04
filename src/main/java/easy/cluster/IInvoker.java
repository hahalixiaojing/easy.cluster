package easy.cluster;

import easy.cluster.invoker.Invocation;

public interface IInvoker {
	String getName();

	<T> T doInvoke(Node node, Invocation invocation, Class<T> cls)
			throws Exception;
}
