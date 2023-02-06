package server;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.IOException;
import java.util.stream.Collectors;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import org.json.JSONException;
import org.json.JSONObject;

import ci.Repository;

public class ContinuousIntegrationServer extends AbstractHandler {
    public void handle(String target,
            Request baseRequest,
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException, ServletException {
        try {
            response.setContentType("text/html;charset=utf-8");
            // response.setStatus(HttpServletResponse.SC_OK);
            baseRequest.setHandled(true);
            String method = request.getMethod();

            switch (method.toUpperCase()) {
                case "GET":
                    // Currently not implemented
                    break;
                case "POST":
                    handleCIRequest(request, response);
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "An exception has occured on the server causing an error.");

        }

    }

    // used to start the CI server in command line
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        server.setHandler(new ContinuousIntegrationServer());
        server.start();
        server.join();
    }

    /**
     * Handles POST requests for CI.
     * 
     * @param request  JSON object
     * @param response
     * @throws IOException
     */
    private void handleCIRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String payload = request.getReader().lines().collect(Collectors.joining());
        try {
            JSONObject json = new JSONObject(payload);
            JSONObject jsonRepo = json.getJSONObject("repository");
            JSONObject jsonOwner = jsonRepo.getJSONObject("owner");

            String repoName = jsonRepo.get("name").toString();
            String commitID = jsonRepo.get("id").toString();
            String sshURL = json.get("ssh_url").toString();
            String user = jsonOwner.get("user").toString();

            String[] spltRef = json.get("ref").toString().split("/");
            String branch = spltRef[spltRef.length - 1];

            Repository repo = new Repository(commitID, repoName, sshURL, branch, user);
            repo.buildRepository();

            response.setStatus(HttpServletResponse.SC_OK);
        } catch (JSONException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong while processing the POST request.");
        }
    }
}