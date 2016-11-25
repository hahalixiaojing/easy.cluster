package easy.cluster.filter;

import easy.cluster.IFilter;
import easy.cluster.IFilterChain;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

public class FilterA implements IFilter {

	@Override
	public <T> T doFilter(Node node, Invocation invocation, Class<T> cls, IFilterChain filterChain) throws Exception {
		System.out.println("A_start");
		long start = System.currentTimeMillis();
		T result = (T) filterChain.doFilter(node, invocation, cls);
		long end = System.currentTimeMillis();
		System.out.println("A_end");
		System.out.println(end - start);
		return result;
	}
}
