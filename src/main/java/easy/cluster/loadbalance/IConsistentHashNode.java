package easy.cluster.loadbalance;

import java.util.List;

import easy.cluster.Node;

public interface IConsistentHashNode {
	Node select(List<Node> nodes,long value);
}
