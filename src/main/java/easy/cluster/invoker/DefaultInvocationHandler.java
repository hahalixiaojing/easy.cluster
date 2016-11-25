package easy.cluster.invoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import easy.cluster.ICluster;
import easy.cluster.IInvoker;
import easy.cluster.ILoadBalance;
import easy.cluster.annoation.Cluster;
import easy.cluster.annoation.Filter;
import easy.cluster.directory.DirectoryFactory;
import easy.cluster.IDirectory;
import easy.cluster.IFilter;
import easy.cluster.loadbalance.LoadBalanceFactory;
import easy.cluster.support.ClusterFactory;
import easy.rpc.http.annonation.RPC;

public class DefaultInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Cluster annCluster = method.getAnnotation(Cluster.class);

		String clusterName = annCluster.cluster();
		String loadbalanceName = annCluster.loadbalance();
		String invoker = this.getInvoker(method);
		String serviceName = getServiceName(method.getDeclaringClass().getName(), annCluster.service());
		String methodName = method.getName();
		IFilter[] filters = this.getFilters(method);
		Invocation invocation = new Invocation(clusterName, loadbalanceName, invoker, serviceName, methodName, method,
				args, filters,"");

		ICluster cluster = ClusterFactory.getCluser(clusterName);
		ILoadBalance loadbalance = LoadBalanceFactory.getloadBalance(loadbalanceName);
		IInvoker invoke = InvokerFactory.getInvoker(invoker);
		IDirectory directory = DirectoryFactory.getDirectory(serviceName);

		return cluster.invoke(directory, args, loadbalance, invoke, method.getReturnType(), invocation);

	}

	private IFilter[] getFilters(Method m) throws Exception {
		Filter annFilters = m.getAnnotation(Filter.class);
		if (annFilters != null) {
			IFilter[] filters = new IFilter[annFilters.filter().length];

			for (int i = 0; i < annFilters.filter().length; i++) {
				filters[i] = annFilters.filter()[i].newInstance();
			}

			return filters;
		}
		return new IFilter[0];
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
