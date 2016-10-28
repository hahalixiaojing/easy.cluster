package easy.cluster;

import java.util.List;

import easy.cluster.invoker.Invocation;

public interface ILoadBalance {
	String getName();

	Node select(List<Node> nodes, Invocation invocation);
}
