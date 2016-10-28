package easy.rpc.http.support;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSON;

import easy.rpc.http.IParameterConverter;
import easy.rpc.http.annonation.Param;

public class DefaultParameterConverter implements IParameterConverter {

	private final java.util.concurrent.ConcurrentHashMap<Method, Parameter[]> methodParamterCached = new ConcurrentHashMap<Method, Parameter[]>();

	@Override
	public String convert(Method m, Object[] args) throws Exception {
		if (args == null || args.length == 0) {
			return "";
		}

		Parameter[] parameters = methodParamterCached.get(m);
		if (parameters == null) {
			parameters = m.getParameters();
			methodParamterCached.putIfAbsent(m, parameters);
		}

		StringBuilder data = new StringBuilder();
		for (int i = 0; i < parameters.length; i++) {
			if (args[i] == null) {
				continue;
			}

			Param p = parameters[i].getAnnotation(Param.class);
			String name = p.name();

			data.append(String.format("%s=%s&", name,
					JSON.toJSONString(args[i])));
		}

		return data.delete(data.length() - 1, data.length()).toString();
	}

	@Override
	public String contentType() {
		return "application/x-www-form-urlencoded;charset=utf-8";
	}
}
