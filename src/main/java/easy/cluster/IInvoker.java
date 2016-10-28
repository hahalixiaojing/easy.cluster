package easy.cluster;

import java.net.URL;

import easy.cluster.invoker.Invocation;

public interface IInvoker {
	String getName();

	<T> T doInvoke(URL url, Invocation invocation, Class<T> cls)
			throws Exception;
}
