package easy.cluster;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import org.junit.*;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import easy.cluster.invoker.Invocation;
import easy.cluster.support.ForkingCluster;

public class ForkingClusterTest {
	ILoadBalance balance;
	ArrayList<Node> nodes;

	IDirectory directory;

	@Before
	public void setUp() {

		nodes = new ArrayList<Node>();

		Node node2 = new Node();
		node2.setAddress("http://a.com.1");

		Node node3 = new Node();
		node3.setAddress("http://a.com.2");

		Node node4 = new Node();
		node4.setAddress("http://a.com.3");

		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);

		balance = EasyMock.createMock(ILoadBalance.class);

		EasyMock.expect(balance.select(EasyMock.isNull(), EasyMock.isNull()))
				.andReturn(nodes.get(new Random().nextInt(3)));

		directory = EasyMock.createMock(IDirectory.class);

		EasyMock.expect(directory.getNodes()).andReturn(nodes).anyTimes();
	}

	@Test
	public void invokeTest() throws Exception {

		EasyMock.replay(balance, directory);

		ForkingCluster forking = new ForkingCluster();
		StringResponse r = forking.invoke(directory, null, balance,
				new IInvoker() {

					@Override
					public String getName() {
						return null;
					}

					@SuppressWarnings("unchecked")
					@Override
					public <T> T doInvoke(URL url, Invocation invocation,
							Class<T> cls) throws Exception {
						if (url.toString().equals("http://a.com.3")
								|| url.toString().equals("http://a.com.2")) {
							System.out.println(url.toString());
							throw new Exception();
						}

						return (T) new StringResponse(url.toString());
					}
				}, StringResponse.class, null);

		Assert.assertEquals("http://a.com.1", r.getName());

	}

	class StringResponse {

		private String name;

		public StringResponse(String name) {
			this.setName(name);
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
