package easy.cluster.full;

import easy.cluster.IService;
import easy.cluster.annoation.Cluster;
import easy.rpc.http.HttpInvoker;
import easy.rpc.http.annonation.Param;
import easy.rpc.http.annonation.RPC;
import easy.rpc.http.annonation.URLPattern;

@URLPattern(url = "http://${address}:${port}/")
@RPC(name = HttpInvoker.NAME)
public interface IUserService extends IService {

	@Cluster
	@RPC(name = HttpJSONInvoker.NAME)
	User getUser(@Param(name = "id") Id id);

	@Cluster
	User[] getUsers(@Param(name = "id") Id id);

	@Cluster
	User[] testUsers(@Param(name = "user") User user,
			@Param(name = "users") User[] users, @Param(name = "id") Integer id);
}
