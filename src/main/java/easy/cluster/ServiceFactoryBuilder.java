package easy.cluster;

import java.lang.reflect.Proxy;

import easy.cluster.invoker.DefaultInvocationHandler;

public class ServiceFactoryBuilder {

	public ServiceFactory build(Class<?>[] serviceInterfaces) {

		ServiceFactory factory = new ServiceFactory();

		DefaultInvocationHandler invocationHandler = new DefaultInvocationHandler();

		for (Class<?> cls : serviceInterfaces) {

			IService service = (IService) Proxy.newProxyInstance(
					cls.getClassLoader(), new Class<?>[] { cls },
					invocationHandler);
			
			factory.register(service);
		}
		return factory;
	}
}
