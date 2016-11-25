package easy.cluster.filter;

import easy.cluster.IFilter;
import easy.cluster.IFilterChain;
import easy.cluster.IInvoker;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

public class DefaultFilterChain implements IFilterChain {

	private IFilter[] filters;
	private int position = -1;
	private IInvoker invoker;

	public DefaultFilterChain(IFilter[] filters, IInvoker invoker) {
		this.filters = filters;
		this.invoker = invoker;
	}

	@Override
	public <T> T doFilter(Node node, Invocation invocation, Class<T> cls) throws Exception {

		position++;
		if (position < filters.length) {
			return filters[position].doFilter(node, invocation, cls, this);
		}
		return this.invoker.doInvoke(node, invocation, cls);
	}
}
