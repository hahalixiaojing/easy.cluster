package easy.rpc.http.support;

import easy.cluster.Node;
import easy.cluster.invoker.Invocation;
import easy.rpc.http.IAPIAddressConverter;
import easy.rpc.http.annonation.URLPattern;

public class DefaultAPIAddressConverter implements IAPIAddressConverter {

	@Override
	public String convert(Invocation invocaton, Node node) {

		URLPattern urlPattern = invocaton.getMethod().getAnnotation(
				URLPattern.class);
		if (urlPattern == null) {
			urlPattern = invocaton.getMethod().getDeclaringClass()
					.getAnnotation(URLPattern.class);
		}

		String url = urlPattern.url().replace("${address}", node.getAddress())
				.replace("${port}", node.getPort());

		String serviceName = invocaton.getServiceName();
		String s = serviceName.substring(serviceName.lastIndexOf(".") + 1);
		return url + "/" + s + "/" + invocaton.getMethodName();
	}

}
