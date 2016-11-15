package easy.cluster.loadbalance;

import java.util.List;
import java.util.stream.Collectors;

import easy.cluster.Node;
/**
 * 一致性哈希算法
 * @author 晓静
 *
 */
public class DefaultConsistenHashNode implements IConsistentHashNode {
	private static final int maxWeight = 160;

	@Override
	public Node select(List<Node> nodes, long value) {
		List<Node> orderedNodes = nodes.stream()
				.sorted((n1, n2) -> n1.getWeight() - n2.getWeight())
				.collect(Collectors.toList());

		int mod = (int) (value % maxWeight);
		Node node = null;
		for (int i = 0; i < orderedNodes.size() - 1; i++) {
			if (mod >= orderedNodes.get(i).getWeight()
					&& mod <= orderedNodes.get(i + 1).getWeight()) {
				if (mod == orderedNodes.get(i).getWeight()) {
					node = orderedNodes.get(i);
					break;
				} else if (mod <= orderedNodes.get(i + 1).getWeight()) {
					node = orderedNodes.get(i + 1);
					break;
				}
			}
		}
		if (node == null) {
			node = orderedNodes.get(0);
		}
		return node;
	}

}
