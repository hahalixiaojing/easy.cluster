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
import easy.rpc.http.annonation.RPC;

public class DefaultInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Cluster annCluster = method.getAnnotation(Cluster.class);

		String clusterName = annCluster.cluster();
		String loadbalanceName = annCluster.loadbalance();
		String invoker = this.getInvoker(method);
		String serviceName = getServiceName(method.getDeclaringClass()
				.getName(), annCluster.service());
		String methodName = method.getName();

		Invocation invocation = new Invocation(clusterName, loadbalanceName,
				invoker, serviceName, methodName, method, args);

		ICluster cluster = ClusterFactory.getCluser(clusterName);
		ILoadBalance loadbalance = LoadBalanceFactory
				.getloadBalance(loadbalanceName);
		IInvoker invoke = InvokerFactory.getInvoker(invoker);
		IDirectory directory = DirectoryFactory.getDirectory(serviceName);
		return cluster.invoke(directory, args, loadbalance, invoke,
				method.getReturnType(), invocation);
	}

	private String getInvoker(Method m) {
		RPC rpc = m.getAnnotation(RPC.class);
		if (rpc == null) {
			rpc = m.getDeclaringClass().getAnnotation(RPC.class);
		}

		return rpc.name();
	}

	private String getServiceName(String clsName, String aliasname) {
		if (aliasname == null || aliasname.length() == 0) {
			return clsName;
		}
		return aliasname;
	}
}
