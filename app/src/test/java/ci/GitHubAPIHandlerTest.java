package ci;

import org.junit.jupiter.api.Test;

import ci.GitHubAPIHandler.STATE;
import okhttp3.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GitHubAPIHandlerTest {
    
    @Test
    /**
     * This test is used to test the commit status can be set successfully on GitHub.
     */
    public void setStatusTest() {
        String repository = "Assignment-1";
        String owner = "DD2480-Group-5";
        String headSha = "31113e4dc5da9b2e01c9c4ee85d2c5c4e9aa96b3";
        String description = "build successful!";
        String targetUrl = "";

        GitHubAPIHandler githubAPIHandler = new GitHubAPIHandler(repository, owner);

        try {
            githubAPIHandler.setState(STATE.SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertTrue(githubAPIHandler.getState() == STATE.SUCCESS);

        Response response = githubAPIHandler.setStatus(headSha, description, targetUrl);
        assertNotNull(response);
        assertTrue(response.isSuccessful());
    }

}
