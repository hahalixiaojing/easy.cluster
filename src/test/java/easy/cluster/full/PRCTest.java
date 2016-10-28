package easy.cluster.full;

import java.util.ArrayList;
import java.util.List;
import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.*;
import easy.cluster.IDirectory;
import easy.cluster.Node;
import easy.cluster.ServiceFactory;
import easy.cluster.ServiceFactoryBuilder;
import easy.cluster.directory.DirectoryFactory;
import easy.cluster.invoker.InvokerFactory;
import easy.rpc.http.HttpInvoker;
import easy.rpc.http.support.DefaultAPIAddressConverter;
import easy.rpc.http.support.DefaultOkHttp;
import easy.rpc.http.support.DefaultParameterConverter;
import easy.rpc.http.support.DefaultReturnConvert;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

public class PRCTest {

	private ServiceFactory sf;

	public PRCTest() throws Exception {
		ServiceFactoryBuilder builder = new ServiceFactoryBuilder();
		sf = builder.build(new Class<?>[] { IUserService.class });

		Node node1 = new Node();
		node1.setAddress("http://192.168.1.1:6935");
		node1.setEnabled(true);

		Node node2 = new Node();
		node2.setAddress("http://localhost:8112/rograndec.act.web/consul");
		node2.setEnabled(true);

		List<Node> nodes = new ArrayList<>();
		nodes.add(node1);
		nodes.add(node2);

		IDirectory directory = EasyMock.createMock(IDirectory.class);

		EasyMock.expect(directory.getServiceName())
				.andReturn(IUserService.class.getName()).anyTimes();
		EasyMock.expect(directory.getNodes()).andReturn(nodes).anyTimes();

		IHttp http = EasyMock.createMock(IHttp.class);

		EasyMock.expect(
				http.execute(EasyMock.endsWith("getUser"), EasyMock.anyString()))
				.andReturn("{\"name\":\"lixiaojing\"}").anyTimes();
		EasyMock.expect(
				http.execute(EasyMock.endsWith("getUsers"),
						EasyMock.anyString())).andAnswer(new IAnswer<String>() {
			@Override
			public String answer() throws Throwable {
				return "[{\"name\":\"lixiaojing\"},{\"name\":\"lixiaojing\"}]";
			}
		}).anyTimes();

		EasyMock.replay(directory, http);

		InvokerFactory.register(new HttpJSONInvoker(http));
		InvokerFactory.register(new HttpInvoker(new DefaultOkHttp(),
				new DefaultParameterConverter(), new DefaultReturnConvert(),
				new DefaultAPIAddressConverter()));
		DirectoryFactory.register(directory);

	}
	@Test
	public void InvokerTest() throws Exception {
		User user = sf.getService(IUserService.class).getUser(new Id("1"));

		Assert.assertNotNull(user);
		Assert.assertEquals(user.getName(), "lixiaojing");

		User[] users = sf.getService(IUserService.class).getUsers(new Id("1"));

		Assert.assertEquals(2, users.length);
	}
	
	
	@Test
	public void realTest() {
		User u1 = new User();
		u1.setName("李晓静");

		User[] user = { u1 };

		User[] uresult = sf.getService(IUserService.class).testUsers(u1, user,
				99999);

		Assert.assertEquals(2, uresult.length);

		sf.getService(IUserService.class).testUsers(u1, user, 99999);
	}
}
