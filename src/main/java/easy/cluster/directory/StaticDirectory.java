package easy.cluster.directory;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import easy.cluster.IDirectory;
import easy.cluster.Node;

public class StaticDirectory implements IDirectory {

	private static final String F_ADDRESS = "nodes.%s.address";
	private static final String F_WEIGHT = "nodes.%s.weight";
	private static final String F_ENABLED = "nodes.%s.enabled";
	private static final String F_PORT = "nodes.%s.port";

	private final String serviceName;
	private final List<Node> nodes;

	public StaticDirectory(String serviceName, Reader reader) throws Exception {
		this.serviceName = serviceName;
		this.nodes = this.parse(reader);
	}

	private List<Node> parse(Reader reader) throws Exception {
		Properties p = new Properties();
		p.load(reader);
		String[] names = p.getProperty("nodes").split(",");

		List<Node> nodes = new ArrayList<Node>();

		for (int i = 0; i < names.length; i++) {
			Node n = this.getNode(p, names[i]);
			nodes.add(n);
		}
		return nodes;
	}

	private Node getNode(Properties p, String node) {
		Node n = new Node();
		n.setAddress(p.getProperty(String.format(F_ADDRESS, node)));
		n.setPort(p.getProperty(String.format(F_PORT, node)));
		n.setWeight(Integer.parseInt(p.getProperty(String
				.format(F_WEIGHT, node))));
		n.setEnabled(Boolean.parseBoolean(p.getProperty(String.format(
				F_ENABLED, node))));
		n.setService(this.serviceName);

		return n;
	}

	@Override
	public String getServiceName() {
		return this.serviceName;
	}

	@Override
	public List<Node> getNodes() {
		return this.nodes;
	}

}
