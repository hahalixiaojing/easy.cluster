package easy.cluster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import easy.cluster.loadbalance.RandomLoadBalance;

public class RandomLoadBalanceTest {

	IDirectory directory;

	public RandomLoadBalanceTest() {
		directory = EasyMock.createMock(IDirectory.class);
	}

	private Node createNode(int weight, String ip) {
		Node node1 = new Node();
		node1.setEnabled(true);
		node1.setService("test");
		node1.setPort("135");
		node1.setWeight(weight);
		node1.setAddress(ip);

		return node1;
	}

	@Test
	public void loadNodeNotSameWeightTest() {
		List<Node> nodes = new ArrayList<>();
		Node node1 = createNode(100, "192");
		Node node2 = createNode(50, "193");
		Node node3 = createNode(25, "194");

		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);

		EasyMock.expect(directory.getNodes()).andReturn(nodes).anyTimes();
		EasyMock.replay(directory);

		RandomLoadBalance balance = new RandomLoadBalance();

		Map<String, Integer> count = new HashMap<>();

		count.put("192", 0);
		count.put("193", 0);
		count.put("194", 0);

		for (int i = 0; i < 10000; i++) {
			Node node = balance.select(directory.getNodes(), null);

			count.compute(node.getAddress(), (k, v) -> ++v);
		}

		int totalCalls = count.values().stream().mapToInt((x) -> x).sum();

		System.out.println("totalCalls=" + totalCalls);
		System.out.println("192=" + count.get("192"));
		System.out.println("193=" + count.get("193"));
		System.out.println("194=" + count.get("194"));
	}

	@Test
	public void loadNodeSameWeightTest() {
		List<Node> nodes = new ArrayList<>();
		Node node1 = createNode(100, "192");
		Node node2 = createNode(100, "193");
		Node node3 = createNode(100, "194");

		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);

		EasyMock.expect(directory.getNodes()).andReturn(nodes).anyTimes();
		EasyMock.replay(directory);

		RandomLoadBalance balance = new RandomLoadBalance();

		Map<String, Integer> count = new HashMap<>();

		count.put("192", 0);
		count.put("193", 0);
		count.put("194", 0);

		for (int i = 0; i < 10000; i++) {
			Node node = balance.select(directory.getNodes(), null);

			count.compute(node.getAddress(), (k, v) -> ++v);
		}

		int totalCalls = count.values().stream().mapToInt((x) -> x).sum();

		System.out.println("totalCalls=" + totalCalls);
		System.out.println("192=" + count.get("192"));
		System.out.println("193=" + count.get("193"));
		System.out.println("194=" + count.get("194"));
	}

}
