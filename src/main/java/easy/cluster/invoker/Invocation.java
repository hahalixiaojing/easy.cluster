package easy.cluster.invoker;

import java.lang.reflect.Method;

import easy.cluster.IFilter;

public class Invocation {
	private String clusterName;
	private String loadbalanceName;
	private String invoker;
	private String serviceName;
	private String methodName;
	private String monitorDataWriter;
	private Method method;
	private Object[] args;
	private IFilter[] filters;

	public Invocation(String clusterName, String loadbalanceName,
			String invoker, String serviceName, String methodName,Method m ,Object[] args,IFilter[] filters,String monitorDataWriter) {

		this.setClusterName(clusterName);
		this.setLoadbalanceName(loadbalanceName);
		this.setInvoker(invoker);
		this.setServiceName(serviceName);
		this.setMethodName(methodName);
		this.setMethod(m);
		this.setArgs(args);
		this.setFilters(filters);
		this.setMonitorDataWriter(monitorDataWriter);
	}

	public String getClusterName() {
		return clusterName;
	}
	public String getLoadbalanceName() {
		return loadbalanceName;
	}
	public String getInvoker() {
		return invoker;
	}
	public String getServiceName() {
		return serviceName;
	}
	public Method getMethod() {
		return method;
	}
	public Object[] getArgs() {
		return args;
	}
	public String getMethodName() {
		return methodName;
	}
	
	protected void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	protected void setLoadbalanceName(String loadbalanceName) {
		this.loadbalanceName = loadbalanceName;
	}
	protected void setInvoker(String invoker) {
		this.invoker = invoker;
	}
	protected void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	protected void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	protected void setMethod(Method method) {
		this.method = method;
	}
	protected void setArgs(Object[] args) {
		this.args = args;
	}

	public IFilter[] getFilters() {
		return filters;
	}

	protected void setFilters(IFilter[] filters) {
		this.filters = filters;
	}

	public String getMonitorDataWriter() {
		return monitorDataWriter;
	}

	protected void setMonitorDataWriter(String monitorDataWriter) {
		this.monitorDataWriter = monitorDataWriter;
	}
}
