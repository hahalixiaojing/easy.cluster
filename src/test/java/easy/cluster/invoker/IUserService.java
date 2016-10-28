package easy.cluster.invoker;


import easy.cluster.annoation.Cluster;

public interface IUserService {
	@Cluster(invoker = "TestInvoker")
	String getUserName(String name);
}
