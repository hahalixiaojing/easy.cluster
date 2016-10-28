package easy.cluster;

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import easy.cluster.directory.StaticDirectory;
import easy.cluster.full.IUserService;

public class StaticDirectoryTest {
	@Test
	public void createTest() throws Exception {

		URL url = this.getClass().getResource("userService.properties");

		FileReader fileReader = new FileReader(new File(url.toURI()));

		StaticDirectory staticDirectory = new StaticDirectory(
				IUserService.class.getName(), fileReader);

		List<Node> nodes = staticDirectory.getNodes();

		Assert.assertEquals(3, nodes.size());

	}
}
