package easy.cluster.invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import easy.cluster.annoation.Cluster;

public class HttpInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		Cluster cluster = method.getAnnotation(Cluster.class);
		
		String clusterName = cluster.cluster();
		String loadbalanceName = cluster.loadbalance();
		String serviceName = method.getDeclaringClass().getName();
		
		
		
		return String.format("%s %s %s", clusterName,loadbalanceName,serviceName);
	}

}
