package easy.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.junit.Test;

import easy.cluster.loadbalance.DefaultConsistenHashNode;

public class DefaultConsistenHashNodeTest {
	@Test
	public void selectTest() {
		DefaultConsistenHashNode ch = new DefaultConsistenHashNode();

		Node node1 = new Node();
		node1.setWeight(1);

		Node node2 = new Node();
		node2.setWeight(2500);

		Node node3 = new Node();
		node3.setWeight(5000);

		Node node4 = new Node();
		node4.setWeight(7500);

		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);

		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		map.put(1, 0);
		map.put(2500, 0);
		map.put(5000, 0);
		map.put(7500, 0);
		
		Random r = new Random();
		
		for (int i = 1; i < 100000; i++) {
			Node node = ch.select(nodes, r.nextInt(100000));
			map.computeIfPresent(node.getWeight(), (k, v) -> ++v);
		}

		for (Entry<Integer, Integer> entry : map.entrySet()) {
			System.out.println(String.format("%s = %s", entry.getKey(),
					entry.getValue()));
		}

	}
}
