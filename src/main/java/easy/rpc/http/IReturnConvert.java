package easy.rpc.http;

public interface IReturnConvert {
	<T> T convert(String data, Class<T> clzz);
}
