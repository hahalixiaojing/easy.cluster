package easy.cluster.invoker;

import org.junit.Test;

public class InvokerTest {

	@Test
	public void proxyTest(){
		
		String name = ServiceProxy.createProxy().getUserName("123");
		
		System.out.println(name);
	}
}
