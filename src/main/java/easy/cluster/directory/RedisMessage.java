package easy.cluster.directory;

import java.util.List;

import easy.cluster.Node;

/**
 * Redis通知节点变化消息类
 * @author 晓静
 *
 */
public class RedisMessage {
	private String serviceName;
	private List<Node> nodes;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public List<Node> getNodes() {
		return nodes;
	}

	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
}
