package easy.cluster.filter;

import easy.cluster.IFilter;
import easy.cluster.IFilterChain;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

public class FilterB implements IFilter {

	@Override
	public <T> T doFilter(Node node, Invocation invocation, Class<T> cls, IFilterChain filterChain) throws Exception {
		System.out.println("B_start");
		T result = filterChain.doFilter(node, invocation, cls);
		System.out.println("B_end");
		return result;
		// return (T)"B";
	}

}
