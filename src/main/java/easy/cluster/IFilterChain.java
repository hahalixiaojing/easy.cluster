package easy.cluster;

import easy.cluster.invoker.Invocation;

public interface IFilterChain {
	<T> T doFilter(Node node,Invocation invocation, Class<T> cls) throws Exception;
}
