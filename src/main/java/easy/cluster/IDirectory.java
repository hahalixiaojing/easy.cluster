package easy.cluster;

import java.util.List;

public interface IDirectory {
	String getServiceName();
	List<Node> getNodes();
}
