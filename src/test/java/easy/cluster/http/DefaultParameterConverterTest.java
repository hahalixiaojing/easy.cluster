package easy.cluster.http;

import java.math.BigDecimal;

import org.junit.Test;

import easy.cluster.full.User;
import easy.rpc.http.support.DefaultParameterConverter;

public class DefaultParameterConverterTest {
	DefaultParameterConverter convert = new DefaultParameterConverter();

	@Test
	public void convertTest() throws Exception {
		Object[] args = new Object[6];

		args[0] = 1;
		args[1] = new User("xlj");
		args[2] = true;
		args[3] = new BigDecimal("25.36");
		args[4] = null;
		args[5] = new User[] { new User("u1"), new User("u2") };

		// Parameter[] ps = this
		// .getClass()
		// .getMethod("testMethod", Integer.class, User.class,
		// Boolean.class, BigDecimal.class, String.class,
		// User[].class).getParameters();

		String obj = convert.convert(
				this.getClass().getMethod("testMethod", Integer.class,
						User.class, Boolean.class, BigDecimal.class,
						String.class, User[].class), args);
	}

	public void testMethod(Integer a, User b, Boolean c, BigDecimal d,
			String e, User[] f) {

	}
	public void testMethod(Integer a, User b) {

	}
}
