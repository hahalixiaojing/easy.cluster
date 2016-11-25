package easy.cluster;

import easy.cluster.invoker.Invocation;

public interface IFilter {
	
	<T> T doFilter(Node node,Invocation invocation, Class<T> cls,IFilterChain filterChain) throws Exception;
}
