package test.rse4j.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import rse4j.commons.client.lookup.RPCLookupService;
import rse4j.commons.client.security.CookieStoreFactory;
import rse4j.demo.services.tree.TreeNode;
import rse4j.demo.services.tree.TreeService;
import rse4j.demo.webapp.application.TestApplication;


@SpringBootTest( //
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
		classes = TestApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class RSE4JTestCase_TreeService {

	private static final String LOOKUP_ENDPOINT = TestConfiguration.TEST_LOOKUP_PATH + "/TreeService";

	@BeforeEach
	public void beforeEach() {
		CookieStoreFactory.getInstance().setCookieStoreProvider(new RSE4JTestCookieStoreProvider());
	}
	
	@Test
	public void testLookupService() throws IOException, URISyntaxException {
		TreeService service = RPCLookupService.lookup(LOOKUP_ENDPOINT);

		assertNotNull(service);
	}

	@Test
	public void testGetTree() throws IOException, URISyntaxException {
		TreeService service = RPCLookupService.lookup(LOOKUP_ENDPOINT);
		
		TreeNode rootTree = service.getRootTreeNode();
		
		assertNotNull(rootTree);
	}
	
	
	@Test
	public void testGetBigTree() throws IOException, URISyntaxException {
		TreeService service = RPCLookupService.lookup(LOOKUP_ENDPOINT);
		
		TreeNode rootTree = service.getBigTree(8, 5);
		
		assertNotNull(rootTree);
	}

	
}
