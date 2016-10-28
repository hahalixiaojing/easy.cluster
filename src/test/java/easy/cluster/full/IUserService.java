package easy.cluster.full;

import easy.cluster.IService;
import easy.cluster.annoation.Cluster;
import easy.rpc.http.HttpInvoker;
import easy.rpc.http.annonation.Param;

public interface IUserService extends IService {

	@Cluster(invoker = HttpJSONInvoker.NAME)
	User getUser(@Param(name = "id") Id id);

	@Cluster(invoker = HttpJSONInvoker.NAME)
	User[] getUsers(@Param(name = "id") Id id);

	@Cluster(invoker = HttpInvoker.NAME)
	User[] testUsers(@Param(name = "user") User user,
			@Param(name = "users") User[] users, @Param(name = "id") Integer id);
}
