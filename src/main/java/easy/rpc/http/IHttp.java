package easy.rpc.http;

public interface IHttp {
	String execute(String url,String data,String contentType) throws Exception;
}
