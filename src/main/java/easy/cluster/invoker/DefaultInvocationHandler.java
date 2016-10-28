package easy.cluster.invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import easy.cluster.ICluster;
import easy.cluster.IInvoker;
import easy.cluster.ILoadBalance;
import easy.cluster.annoation.Cluster;
import easy.cluster.directory.DirectoryFactory;
import easy.cluster.IDirectory;
import easy.cluster.loadbalance.LoadBalanceFactory;
import easy.cluster.support.ClusterFactory;

public class DefaultInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Cluster rpc = method.getAnnotation(Cluster.class);

		String clusterName = rpc.cluster();
		String loadbalanceName = rpc.loadbalance();
		String invoker = rpc.invoker();
		String serviceName = getServiceName(method.getDeclaringClass()
				.getName(), rpc.service());
		String methodName = method.getName();

		Invocation invocation = new Invocation(clusterName, loadbalanceName,
				invoker, serviceName, methodName,method,args);

		ICluster cluster = ClusterFactory.getCluser(clusterName);
		ILoadBalance loadbalance = LoadBalanceFactory
				.getloadBalance(loadbalanceName);
		IInvoker invoke = InvokerFactory.getInvoker(invoker);
		IDirectory directory = DirectoryFactory.getDirectory(serviceName);
		return cluster.invoke(directory, args, loadbalance, invoke,
				method.getReturnType(), invocation);
	}

	private String getServiceName(String clsName, String aliasname) {
		if (aliasname == null || aliasname.length() == 0) {
			return clsName;
		}
		return aliasname;
	}
}
