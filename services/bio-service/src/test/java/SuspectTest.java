/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import com.geocent.codeathon.bio.info.Utils.JsonHelper;
import static com.geocent.codeathon.bio.info.datastore.Database.createMockSuspect;
import com.geocent.codeathon.bio.info.model.Suspect;
import java.io.IOException;
import junit.framework.Assert;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author aaronwhitney
 */
public class SuspectTest {
	
	private static String BASE_URL = "http://localhost:8080/bio-service/services/";
	
	public SuspectTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}
	// TODO add test methods here.
	// The methods must be annotated with annotation @Test. For example:
	//
	// @Test
	// public void hello() {}
	
//	@Test
	public void testCreateSuspect() throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        StringBuilder url = new StringBuilder(BASE_URL);
		url.append("suspect/");
		HttpPost httpPost = new HttpPost(url.toString());
		
        EntityBuilder entityBuilder = EntityBuilder.create();
        entityBuilder.setContentType(ContentType.APPLICATION_JSON);

		Suspect aaron = createMockSuspect("982c4a67-bb39-41f9-9872-88356822b7ad", "Aaron");
		JsonHelper jsonHelper = new JsonHelper();
		String jsonRequest = jsonHelper.convertToJson(aaron);
		
        entityBuilder.setText(jsonRequest);
        httpPost.setEntity(entityBuilder.build());
        CloseableHttpResponse response = httpclient.execute(httpPost);
		Assert.assertTrue(true);
	}
}