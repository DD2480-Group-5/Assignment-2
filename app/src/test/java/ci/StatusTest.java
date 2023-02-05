package ci;

import okhttp3.Response;
import org.junit.jupiter.api.Test;

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
        // String branch = "issue/105_zihao";

        Status status = new Status(name, id, Status.Possible_state.INIT, url, "description", "BillXu0424");

        assertNotNull(status);

        status.setStatus(Status.Possible_state.PENDING);

        assertEquals(status.getState(), Status.Possible_state.PENDING);

        Response response = status.createCommitStatus();

        assertTrue(response.isSuccessful());
    }
}
