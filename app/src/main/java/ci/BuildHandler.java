package ci;

import org.eclipse.jetty.server.Request;

import java.io.File;

public class BuildHandler {

    /**
     * Building cloned repository.
     */
    public static void runBuild(Request baseRequest) {
        /* get payload from HTTP request and create JSON object */
        String payload = baseRequest.getParameter("payload");
    }

    private static String runCheck(File path) {
        return "";
    }
}
