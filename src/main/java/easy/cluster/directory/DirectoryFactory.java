package easy.cluster.directory;

import java.util.HashMap;
import java.util.Map;

import easy.cluster.IDirectory;

public class DirectoryFactory {
	private static final Map<String, IDirectory> directories = new HashMap<>();

	public static void register(IDirectory directory) {
		directories.put(directory.getServiceName(), directory);
	}
	
	public static IDirectory getDirectory(String name){
		return directories.get(name);
	}
}
