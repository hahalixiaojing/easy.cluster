package easy.cluster.support;

import java.net.URL;

import easy.cluster.ICluster;
import easy.cluster.IDirectory;
import easy.cluster.IInvoker;
import easy.cluster.ILoadBalance;
import easy.cluster.Node;
import easy.cluster.invoker.Invocation;
/**
 * 失败安全
 * @author 晓静
 *
 */
public class FailsafeCluster implements ICluster {
	
	public static final String NAME ="FailsafeCluster";
	@Override
	public String getName() {
		return NAME;
	}

	@Override
	public <T> T invoke(IDirectory directory, Object[] paramter,
			ILoadBalance loadbanlance, IInvoker invoker, Class<T> cls,
			Invocation invocation) throws Exception {
		
		Node node = loadbanlance.select(directory.getNodes(), invocation);
		
		try{
			return invoker.doInvoke(new URL(node.getAddress()), invocation, cls);
		}
		catch(Exception e){
			return null;
		}
	}

}
