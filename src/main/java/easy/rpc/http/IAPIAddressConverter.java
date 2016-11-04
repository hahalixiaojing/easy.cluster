package easy.rpc.http;


import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

public interface IAPIAddressConverter {
	String convert(Invocation invocaton,Node node);
}
