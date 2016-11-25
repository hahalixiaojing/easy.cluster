package easy.cluster.filter;

import org.junit.Test;
import easy.cluster.*;
import easy.cluster.invoker.Invocation;

public class FilterTest {
	@Test
	public void filter() throws Exception {

		IFilter[] filters = { new FilterA(), new FilterB(), new FilterC() };

		DefaultFilterChain chain = new DefaultFilterChain(filters, new IInvoker() {

			@Override
			public String getName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T doInvoke(Node node, Invocation invocation, Class<T> cls) throws Exception {
				Thread.sleep(1000);
				System.out.println("Cluster");
				return (T) "Cluster";
			}
		});

		String r = chain.doFilter(null, null, null);

		System.out.println(String.format("返回值:%s", r));

	}
}
