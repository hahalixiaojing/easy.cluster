package easy.cluster;

import java.util.HashMap;
import java.util.Map;

public  class ServiceFactory {
	public static final Map<String, IService> services = new HashMap<>();

	protected  void register(IService service) {
		services.put(service.getClass().getInterfaces()[0].getName(), service);
	}

	@SuppressWarnings("unchecked")
	public  <T extends IService> T getService(Class<T> cls) {
		return (T) services.get(cls.getName());
	}
}
