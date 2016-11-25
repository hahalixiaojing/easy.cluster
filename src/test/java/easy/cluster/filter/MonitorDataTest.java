package easy.cluster.filter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.AfterClass;
import org.junit.Test;

import org.junit.*;

public class MonitorDataTest {

	static final ExecutorService excutor = Executors.newCachedThreadPool();

	@Test
	public void threadTest() throws Exception {
		MonitorData data = new MonitorData();
		for (int i = 0; i < 1000; i++) {

			excutor.execute(() -> {

				data.incrementErrorFrequency();
				data.incrementRequestFrequency();
				data.incrementResponseFrequency();
			});
		}
		
		Thread.sleep(3000);
		
	

		Assert.assertEquals(1000, data.getRequestFrequency());
		Assert.assertEquals(1000, data.getResponseFrequency());
		Assert.assertEquals(1000, data.getRequestFrequency());
	}

	@AfterClass
	public static void after() {
		excutor.shutdown();
	}
}
