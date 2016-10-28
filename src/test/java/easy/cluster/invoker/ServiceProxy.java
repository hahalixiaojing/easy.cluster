package easy.cluster.invoker;

import java.lang.reflect.Proxy;

public class ServiceProxy {
	public static IUserService createProxy() {

		return (IUserService) Proxy.newProxyInstance(
				IUserService.class.getClassLoader(),
				new Class<?>[] { IUserService.class },
				new HttpInvocationHandler());
	}
	
	
}
