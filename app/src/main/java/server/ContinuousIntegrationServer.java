package server;

import ci.GitHubAPIHandler;
import ci.GitHubAPIHandler.STATE;
import ci.History;
import ci.Repository;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.stream.Collectors;

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
        Server server = new Server(8005);
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
        String payload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        payload = payload.substring("payload=".length());
        payload = URLDecoder.decode(payload, "UTF-8");

        try {
            JSONObject json = new JSONObject(payload);

            /* deleting branch after merge pull request will also send a request */
            if (json.isNull("head_commit")) return;

            JSONObject jsonRepo = json.getJSONObject("repository");
            JSONObject jsonOwner = jsonRepo.getJSONObject("owner");

            String repoName = jsonRepo.get("name").toString();
            String commitID = json.get("after").toString();
            String sshURL = jsonRepo.get("ssh_url").toString();
            String username = jsonOwner.get("name").toString();

            // get field for persisting storage
            JSONObject commitsField = json.getJSONArray("commits").getJSONObject(0);
            String message = commitsField.get("message").toString();
            String timestamp = commitsField.get("timestamp").toString();
            String commitURL = commitsField.get("url").toString();
            String committer = commitsField.getJSONObject("committer").get("name").toString();
            JSONArray added = commitsField.getJSONArray("added");
            JSONArray removed = commitsField.getJSONArray("removed");
            JSONArray modified = commitsField.getJSONArray("modified");
            StringBuilder commitInfo_sb = new StringBuilder();
            if (!added.isNull(0)) {
                for (int i = 0; i < added.length(); i++) {
                    commitInfo_sb.append("Added: ").append(added.getString(i)).append('\n');
                }
            }
            if (!removed.isNull(0)) {
                for (int i = 0; i < removed.length(); i++) {
                    commitInfo_sb.append("Removed: ").append(removed.getString(i)).append('\n');
                }
            }
            if (!modified.isNull(0)) {
                for (int i = 0; i < modified.length(); i++) {
                    commitInfo_sb.append("Modified: ").append(modified.getString(i)).append('\n');
                }
            }
            String commitInfo = commitInfo_sb.toString();


            String[] spltRef = json.get("ref").toString().split("/");
            String branch;
            /* some branch name may contain '/' */
            if (spltRef.length > 3) {
                branch = spltRef[2];
                for (int i = 3; i < spltRef.length; i++) {
                    branch = branch.concat("/" + spltRef[i]);
                }
            } else {
                branch = spltRef[spltRef.length - 1];
            }

            Repository repo = new Repository(commitID, repoName, sshURL, branch, username);
            GitHubAPIHandler handler = new GitHubAPIHandler(repoName, username);
            handler.setState(STATE.PENDING);
            handler.setStatus(commitID, "Building app...", "");
            STATE exitState = repo.buildRepository();
            handler.setState(exitState);

            History history = new History();

            switch (handler.getState()) {
                case SUCCESS:
                    history.saveBuild(commitID, "success", message, timestamp, commitURL, committer, commitInfo);
                    handler.setStatus(commitID, "Build and test successful!", "");
                    break;
                case FAILURE:
                    history.saveBuild(commitID, "failure", message, timestamp, commitURL, committer, commitInfo);
                    handler.setStatus(commitID, "Build and test failed!", "");
                    break;
                default:
                    handler.setState(STATE.FAILURE);
                    handler.setStatus(commitID, "Something went wrong.", "");
                    break;
            }

            response.setStatus(HttpServletResponse.SC_OK);

        } catch (JSONException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong while processing the POST request.");
            e.printStackTrace();
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Something went wrong while setting commit status.");
            e.printStackTrace();
        }
    }
}