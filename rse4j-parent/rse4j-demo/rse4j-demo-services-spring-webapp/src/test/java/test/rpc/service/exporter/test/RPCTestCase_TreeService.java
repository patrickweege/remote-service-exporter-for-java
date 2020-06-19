package test.rpc.service.exporter.test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import rpc.service.exporter.commons.client.lookup.RPCLookupService;
import rpc.service.exporter.commons.client.security.CookieStoreFactory;
import rpc.service.exporter.test.services.tree.TreeNode;
import rpc.service.exporter.test.services.tree.TreeService;
import rpc.service.exporter.test.webapp.application.TestApplication;


@SpringBootTest( //
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
		classes = TestApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class RPCTestCase_TreeService {

	private static final String LOOKUP_ENDPOINT = "http://localhost:8080/rpcServiceExporterTest/lookup/TreeService";

	@BeforeEach
	public void beforeEach() {
		CookieStoreFactory.getInstance().setCookieStoreProvider(new DefaultTestCookieStoreProvider());
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
