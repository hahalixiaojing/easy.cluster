package easy.cluster.full;

import java.lang.reflect.Array;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import easy.cluster.IInvoker;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;

public class HttpJSONInvoker implements IInvoker {

	public static final String NAME = "HttpJSONInvoker";

	private final IHttp http;

	public HttpJSONInvoker() {
		http = new DefaultHttp();
	}

	public HttpJSONInvoker(IHttp http) {
		this.http = http;
	}

	@Override
	public String getName() {
		return NAME;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T doInvoke(Node node, Invocation invocation,Class<T> cls
			) throws Exception {

		String json = JSON.toJSONString(invocation.getArgs()[0]);

		String u = node.getAddress()
				+ path(invocation.getServiceName(), invocation.getMethodName());

		String jsondata = this.http.execute(u, json);

		if (jsondata == null || jsondata.trim() == "") {
			return null;
		}

		if (cls.isArray()) {

			Class<?> arrayType = cls.getComponentType();

			JSONArray jsonArray = JSON.parseArray(jsondata);
			Object array = Array.newInstance(arrayType, jsonArray.size());

			for (int i = 0; i < jsonArray.size(); i++) {

				Object value = jsonArray.getObject(i, arrayType);
				Array.set(array, i, value);
			}
			return (T) array;
		}
		return JSON.parseObject(jsondata, cls);
	}

	private String path(String serviceName, String methodName) {
		String s = serviceName.substring(serviceName.lastIndexOf(".") + 1);

		return "/" + s + "/" + methodName;
	}

	static class DefaultHttp implements IHttp {
		private static final OkHttpClient client = new OkHttpClient();
		private static final MediaType JSON_MEDIA_TYPE = MediaType
				.parse("application/json; charset=utf-8");

		@Override
		public String execute(String url, String data) throws Exception {
			RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, data);
			Request r = new Request.Builder().url(url).post(body).build();
			Response response = client.newCall(r).execute();

			return response.body().string();
		}

	}
}
