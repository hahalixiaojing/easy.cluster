package easy.rpc.http;

import java.lang.reflect.*;

public interface IParameterConverter {
	String convert(Method m, Object[] args) throws Exception;

	String contentType();
}
