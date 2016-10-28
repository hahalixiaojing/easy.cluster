package easy.cluster.invoker;

import java.util.HashMap;
import java.util.Map;

import easy.cluster.IInvoker;

public abstract class InvokerFactory {
	private static final Map<String, IInvoker> invokers = new HashMap<>();

	static {
		
	}

	public static void register(IInvoker invoker) {
		invokers.put(invoker.getName(), invoker);
	}
	
	public static IInvoker getInvoker(String name){
		return invokers.get(name);
	}
}
