package easy.rpc.http;

import java.net.URL;

import easy.cluster.invoker.Invocation;

public interface IAPIAddressConverter {
	String convert(Invocation invocaton,URL url);
}
