package ci;

import okhttp3.Response;
import org.junit.jupiter.api.Test;

import ci.GitHubAPIHandler.STATE;

import static org.junit.jupiter.api.Assertions.*;

public class StatusTest {
    @Test
    /*
      This test is used to test the Status class.
     */
    public void StatusMethodsTest() throws Exception {
        String id = "31113e4dc5da9b2e01c9c4ee85d2c5c4e9aa96b3";
        String name = "Assignment-1";
        String url = "git@github.com:DD2480-Group-5/Assignment-1.git";
        String owner = "BillXu0424";

        GitHubAPIHandler handler = new GitHubAPIHandler(name, owner);

        assertNotNull(handler.getState());

        handler.setState(STATE.PENDING);

        assertEquals(handler.getState(), STATE.PENDING);
		
		assertTrue(false);

        /* please note that the targetUrl is different ssh-url of the repo, it's related to the CI server. */
        // Response response = handler.setStatus(id, "ci-test", targetUrl);

        // assertTrue(response.isSuccessful());
    }
}
