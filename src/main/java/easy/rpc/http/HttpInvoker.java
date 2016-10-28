package easy.rpc.http;

import java.net.URL;

import easy.cluster.IInvoker;
import easy.cluster.invoker.Invocation;

public class HttpInvoker implements IInvoker {

	public static final String NAME = "HttpInvoker";

	private final IHttp http;
	private final IParameterConverter parameterConverter;
	private final IReturnConvert returnConverter;
	private final IAPIAddressConverter apiAddressConvert;

	public HttpInvoker(IHttp http, IParameterConverter paramterConvert,
			IReturnConvert returnConvert, IAPIAddressConverter apiAddressConvert) {
		this.http = http;
		this.parameterConverter = paramterConvert;
		this.returnConverter = returnConvert;
		this.apiAddressConvert = apiAddressConvert;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public <T> T doInvoke(URL url, Invocation invocation, Class<T> cls)
			throws Exception {

		String apiAddress = this.apiAddressConvert.convert(invocation, url);

		String parameter = this.parameterConverter.convert(
				invocation.getMethod(), invocation.getArgs());

		String result = this.http.execute(apiAddress, parameter,
				this.parameterConverter.contentType());

		return (T) this.returnConverter.convert(result, cls);

	}

}
