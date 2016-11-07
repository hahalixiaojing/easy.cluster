package easy.cluster.invoker;

import easy.cluster.annoation.Cluster;
import easy.rpc.http.annonation.RPC;

public interface IUserService {
	@Cluster()
	@RPC(name = "TestInvoker")
	String getUserName(String name);
}
