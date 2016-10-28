package easy.cluster.loadbalance;

import java.util.List;
import java.util.Random;

import easy.cluster.ILoadBalance;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

public class RandomLoadBalance implements ILoadBalance {

	public static final String NAME = "RandomLoadBalance";
	private final Random random = new Random();

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public Node select(List<Node> nodes,Invocation invocation) {
		int length = nodes.size();
		int totalWeight = 0;
		boolean isSameWeight = true;

		for (int i = 0; i < length; i++) {
			int weight = nodes.get(i).getWeight();
			totalWeight += weight;
			if (isSameWeight && i > 0 && weight != nodes.get(i - 1).getWeight()) {
				isSameWeight = false;
			}
		}

		if (totalWeight > 0 && !isSameWeight) {
			int offset = random.nextInt(totalWeight);
			for (int i = 0; i < length; i++) {
				offset -= nodes.get(i).getWeight();
				if (offset < 0) {
					return nodes.get(i);
				}
			}
		}
		return nodes.get(random.nextInt(length));
	}

}
