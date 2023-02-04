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
        String id = "b5ab2152ed2764b423d6106da7f0c8146a0278b3";
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
