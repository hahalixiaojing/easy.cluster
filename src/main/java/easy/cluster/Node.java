package easy.cluster;

public class Node {
	private String service;
	private String address;
	private int weight;
	private boolean enabled;
	private String port;

	public String getService() {
		return service;
	}

	public void setService(String servcie) {
		this.service = servcie;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	public int hashCode() {
		return this.getAddress().hashCode() ^ this.getPort().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (Node.class != obj.getClass()) {
			return false;
		}
		Node node = (Node) obj;
		if (!this.getAddress().toLowerCase()
				.equals(node.getAddress().toLowerCase())) {
			return false;
		}
		if(!this.getPort().equals(node.getPort())){
			return false;
		}
		return true;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	
	public boolean getEnabled() {
		return enabled;
	}

	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
