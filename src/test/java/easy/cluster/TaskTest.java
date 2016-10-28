package easy.cluster;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.*;

import org.junit.Test;

public class TaskTest {

	@Test
	public void test() throws Exception {

		Callable<String> c1 = () -> {
			throw new Exception();
		};

		Callable<String> c2 = () -> {
			return "1";
		};
		ArrayList<Callable<String>> callable = new ArrayList<>();
		callable.add(c1);
		callable.add(c2);

		List<Future<String>> f = Executors.newCachedThreadPool().invokeAll(
				callable);

		String result = "";
		for (Future<String> ff : f) {
			try {
				result = ff.get();
				
				
				break;
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		System.out.println(result);
	}
	@Test
	public void andTest(){
		
		int a = 3 & 1;
		Assert.assertEquals(1, a);
		
		int c = 3 | 16;
		
		Assert.assertEquals(0, c & 4);
		Assert.assertEquals(16, c & 16);
		//取消用户权限
		int d = c & (~16);
		Assert.assertEquals(3,d);
		
		int e = 3 | 16 | 128 ;
		
		Assert.assertEquals(147, e);
		
		d = c & (~16) & (~128);
		Assert.assertEquals(3,d);

	}
}
