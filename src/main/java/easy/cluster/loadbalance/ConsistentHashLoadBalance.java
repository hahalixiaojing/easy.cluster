package easy.cluster.loadbalance;

import java.lang.reflect.Parameter;
import java.util.List;

import easy.cluster.ILoadBalance;
import easy.cluster.Node;
import easy.cluster.annoation.ConsistentHashKeyParam;
import easy.cluster.invoker.Invocation;

public class ConsistentHashLoadBalance implements ILoadBalance {
	public static final String NAME = "ConsistentHashLoadBalance";
	private final IConsistentHashNode consistenHash;

	@Override
	public String getName() {
		return NAME;
	}

	public ConsistentHashLoadBalance(IConsistentHashNode consistenHash) {
		this.consistenHash = consistenHash;
	}

	@Override
	public Node select(List<Node> nodes, Invocation invocation) {
		Long value = this.getConsistentHashKeyValue(invocation.getMethod()
				.getParameters(), invocation.getArgs());

		return this.consistenHash.select(nodes, value);
	}

	private Long getConsistentHashKeyValue(Parameter[] parameters, Object[] args) {

		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].getAnnotation(ConsistentHashKeyParam.class) instanceof ConsistentHashKeyParam) {
				return Long.parseLong(args[i].toString());
			}
		}
		return 0L;
	}
}
