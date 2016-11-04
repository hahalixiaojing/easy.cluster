package easy.cluster;

import java.util.ArrayList;
import java.util.Random;

import org.junit.*;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import easy.cluster.invoker.Invocation;
import easy.cluster.support.*;

public class FailoverClusterTest {
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

		EasyMock.expect(balance.select(EasyMock.anyObject(), EasyMock.isNull()))
				.andReturn(nodes.get(new Random().nextInt(3))).times(1, 3);

		directory = EasyMock.createMock(IDirectory.class);
		EasyMock.expect(directory.getNodes()).andReturn(nodes).anyTimes();
	}

	@Test
	public void test() throws Exception {

		EasyMock.replay(balance, directory);

		FailoverCluster cluster = new FailoverCluster();
		StringResponse result = cluster.invoke(directory, null, balance,
				new IInvoker() {

					@Override
					public String getName() {
						return null;
					}

					@SuppressWarnings("unchecked")
					@Override
					public <T> T doInvoke(Node node, Invocation invocation,
							Class<T> cls) throws Exception {
						return (T) new StringResponse(node.getAddress());
					}

				}, StringResponse.class, null);

		Assert.assertTrue(result.getName().equals("http://a.com.1")
				|| result.getName().equalsIgnoreCase("http://a.com.2"));

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
