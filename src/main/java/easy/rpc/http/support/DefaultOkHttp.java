package easy.rpc.http.support;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import easy.rpc.http.IHttp;

public class DefaultOkHttp implements IHttp {
	private static final OkHttpClient client = new OkHttpClient();

	@Override
	public String execute(String url, String data, String contentType)
			throws Exception {

		RequestBody body = RequestBody.create(MediaType.parse(contentType),
				data);
		Request r = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(r).execute();

		return response.body().string();
	}

}
