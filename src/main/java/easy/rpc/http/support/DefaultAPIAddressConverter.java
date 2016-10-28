package easy.rpc.http.support;

import java.net.URL;

import easy.cluster.invoker.Invocation;
import easy.rpc.http.IAPIAddressConverter;

public class DefaultAPIAddressConverter implements IAPIAddressConverter {

	@Override
	public String convert(Invocation invocaton, URL url) {
		String serviceName = invocaton.getServiceName();
		String s = serviceName.substring(serviceName.lastIndexOf(".") + 1);
		return url.toString() + "/" + s + "/" + invocaton.getMethodName();
	}

}
