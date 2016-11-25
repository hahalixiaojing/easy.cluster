package easy.cluster.filter;

import easy.cluster.IFilter;
import easy.cluster.IFilterChain;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

public class FilterC implements IFilter {

	@Override
	public <T> T doFilter(Node node, Invocation invocation, Class<T> cls, IFilterChain filterChain) throws Exception {
		System.out.println("C_start");
		T result = filterChain.doFilter(node, invocation, cls);
		System.out.println("C_end");
		return result;

		// return (T) "C";
	}

}
