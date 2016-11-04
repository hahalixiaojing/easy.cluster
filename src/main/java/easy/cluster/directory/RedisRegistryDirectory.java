package easy.cluster.directory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import easy.cluster.IDirectory;
import easy.cluster.IRefreshDirectoryNodes;
import easy.cluster.Node;

public class RedisRegistryDirectory implements IDirectory, IRefreshDirectoryNodes {

	private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	private final List<Node> nodeList = new ArrayList<>();
	private final String serviceName;

	public RedisRegistryDirectory(String serviceName, List<Node> nodes) {
		this.serviceName = serviceName;
		this.nodeList.addAll(nodes);
	}

	@Override
	public String getServiceName() {
		return this.serviceName;
	}

	@Override
	public List<Node> getNodes() {
		lock.readLock().lock();
		try {
			return this.nodeList;
		} finally {
			lock.readLock().unlock();
		}
	}

	@Override
	public void refresh(List<Node> nodes) {
		lock.writeLock().lock();
		try {
			this.nodeList.clear();
			for (Node node : nodes) {
				this.nodeList.add(node);
			}
		} finally {
			lock.writeLock().unlock();
		}
	}

}
