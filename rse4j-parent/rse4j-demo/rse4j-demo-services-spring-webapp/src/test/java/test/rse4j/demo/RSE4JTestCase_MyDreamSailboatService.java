package test.rse4j.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import rse4j.commons.client.exception.RPCException;
import rse4j.commons.client.lookup.RPCLookupService;
import rse4j.commons.client.security.CookieStoreFactory;
import rse4j.demo.services.dreamsailboat.DreamSailboatService;
import rse4j.demo.webapp.application.TestApplication;

@SpringBootTest( //
		webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, //
		classes = TestApplication.class)
@TestPropertySource(locations = "classpath:application.properties")
public class RSE4JTestCase_MyDreamSailboatService {

	private static final String LOOKUP_ENDPOINT = TestConfiguration.TEST_LOOKUP_PATH + "/MyDreamSailBoatList";

	@BeforeEach
	public void beforeEach() {
		CookieStoreFactory.getInstance().setCookieStoreProvider(new RSE4JTestCookieStoreProvider());
	}
	
	@Test
	public void testLookupHelloWorldBean() throws IOException, URISyntaxException {
		DreamSailboatService helloWorldBean = RPCLookupService.lookup(LOOKUP_ENDPOINT);

		assertNotNull(helloWorldBean);
	}

	@Test
	public void testGetBoatList() throws IOException, URISyntaxException {
		DreamSailboatService service = RPCLookupService.lookup(LOOKUP_ENDPOINT);

		List<String> boatList = service.getBoatList();

		assertEquals(3, boatList.size());
	}

	@Test
	public void testGetFirst() throws IOException, URISyntaxException {
		DreamSailboatService service = RPCLookupService.lookup(LOOKUP_ENDPOINT);

		String first = service.getFirst();

		assertEquals("Gunboat", first);
	}

	@Test
	public void testAddAtFirst() throws IOException, URISyntaxException {
		DreamSailboatService service = RPCLookupService.lookup(LOOKUP_ENDPOINT);

		service.addAt("Will-Ich-Sau", 0);

		String first = service.getFirst();

		assertEquals("Will-Ich-Sau", first);
	}

	@Test
	public void testAddAtInvalidIndex() throws IOException, URISyntaxException {
		DreamSailboatService service = RPCLookupService.lookup(LOOKUP_ENDPOINT);

		assertThrows(RPCException.class, () -> {
			service.addAt("Invalid Index", -1);
		});

	}

}
