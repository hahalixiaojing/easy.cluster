package easy.cluster.invoker;


public class InvokerResult<T> {
	private String status;
	private String message;
	private T data;

	public InvokerResult(String status, String message, T data) {
		this.setStatus(status);
		this.setMessage(message);
		this.setData(data);
	}
	public T getData() {
		return this.data;
	}

	public String getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	protected void setData(T data) {
		this.data = data;
	}

	protected void setStatus(String status) {
		this.status = status;
	}
}
