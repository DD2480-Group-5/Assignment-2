package ci;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HistoryTest {

    @Test
    /**
     * This test is used to test saveBuild method can be done successfully.
     * @throws IOException
     */
    public void testSaveBuild() throws IOException {
        String commitID = "18ce5121bba10bf0b784bcc04a8e87a7100bb24c";
        String message = "test for ass 4";
        String timestamp = "2023-02-08T16:13:58+01:00";
        String commitURL = "https://github.com/DD2480-Group-5/Assignment-1/commit/18ce5121bba10bf0b784bcc04a8e87a7100bb24c";
        String committer = "LMC117";
        String commitInfo = "Modified - README.md";

        History history = new History();

        assertTrue(history.saveBuild(commitID, "success", message, timestamp, commitURL, committer, commitInfo, false));
    }
}
